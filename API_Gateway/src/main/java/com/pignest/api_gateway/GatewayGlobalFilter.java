package com.pignest.api_gateway;


import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alibaba.nacos.shaded.com.google.gson.reflect.TypeToken;
import com.pignest.api_client_sdk.utils.SignUtils;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.common.InterfaceStatusEnum;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_common.model.dto.RequestParamsField;
import com.pignest.api_common.model.entity.InterfaceInfo;
import com.pignest.api_common.model.vo.UserVO;
import com.pignest.api_common.service.inner.InnerInterfaceInfoService;
import com.pignest.api_common.service.inner.InnerUserInterfaceInvokeService;
import com.pignest.api_common.service.inner.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.pignest.api_client_sdk.constant.SignConstant.*;
import static com.pignest.api_common.common.UserRoleEnum.BAN;

/**
 * @author Black
 */
@Component
@Slf4j
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 请求白名单
     */
    private final static List<String> WHITE_HOST_LIST = Collections.singletonList("127.0.0.1");

    /**
     * 五分钟过期时间
     */
    private static final long FIVE_MINUTES = 5L * 60;


    @DubboReference
    private InnerUserService innerUserService;
    @DubboReference
    private InnerUserInterfaceInvokeService interfaceInvokeService;
    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一id：" + request.getId());
        log.info("请求参数：" + request.getQueryParams());
        log.info("请求体：" + request.getBody());
        log.info("请求方法：" + request.getMethod());
        log.info("请求路径：" + request.getPath());
        log.info("网关本地地址：" + request.getLocalAddress());
        log.info("请求远程地址：" + request.getRemoteAddress());
        log.info("url:" + request.getURI());
        return verifyParameters(exchange, chain);
    }

    /**
     * 验证参数
     *
     * @param exchange 交换
     * @param chain    链条
     * @return {@link Mono}<{@link Void}>
     */
    private Mono<Void> verifyParameters(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 请求白名单
//        if (!WHITE_HOST_LIST.contains(Objects.requireNonNull(request.getRemoteAddress()).getHostString())) {
//            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
//        }

        HttpHeaders headers = request.getHeaders();
        //MultiValueMap<String,String> query =  request.getQueryParams();
        String body = headers.getFirst(BODY);
        String accessKey = headers.getFirst(ACCESSKEY);
        String timestamp = headers.getFirst(TIMESTAMP);
        String sign = headers.getFirst(SIGN);
        String nonce =  headers.getFirst(NONCE);
        log.info("body："+body);
        log.info("ACCESSKEY: "+accessKey);
        log.info("TIMESTAMP: "+timestamp);
        log.info("SIGN: "+sign);
        log.info("NONCE: "+nonce);
        // 请求头中参数必须完整
        if (StringUtils.isAnyBlank(body, sign, accessKey, timestamp)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
        }
        // 防重发XHR
        long currentTime = System.currentTimeMillis() / 1000;
        assert timestamp != null;
        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "会话已过期,请重试！");
        }
        try {
            UserVO user = innerUserService.getInvokeUserByAccessKey(accessKey);
            if (ObjectUtils.isEmpty(user)){
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "该秘钥对应的账号不存在，请检查秘钥是否有误，或去个人中心更新凭证");
            }
            // 校验accessKey
            if (!user.getAccessKey().equals(accessKey)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "请先获取请求密钥");
            }
            if (user.getUserRole().equals(BAN.getValue())) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "该账号已封禁");
            }
            if (user.getPigCoins() <= 0) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "余额不足，请先充值。");
            }
            //map封装
            //Map<String, String> hashMap = query.toSingleValueMap();
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put(ACCESSKEY, accessKey);
            hashMap.put(TIMESTAMP, timestamp);
            hashMap.put(NONCE, nonce);
            if (StringUtils.isNotBlank(body)) {
                hashMap.put("body", body);
            }
            String secretKey = user.getSecretKey();
            // 校验签名
            if (!SignUtils.signValidate(hashMap, secretKey, sign)) {//认证失败
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "非法请求");
            }
            //---接口校验---
            String method = Objects.requireNonNull(request.getMethod()).toString();
            String uri = request.getURI().getPath().trim();

            if (StringUtils.isAnyBlank(uri, method)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            InterfaceInfo interfaceInfo = interfaceInfoService.getInterfaceInfo(uri, method);
            if (interfaceInfo == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口不存在");
            }
            if (interfaceInfo.getStatus() == InterfaceStatusEnum.AUDITING.getValue()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口审核中");
            }
            if (interfaceInfo.getStatus() == InterfaceStatusEnum.OFFLINE.getValue()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口未开启");
            }
            // 校验请求参数
            String requestParams = interfaceInfo.getRequestParams();
            if(StringUtils.isNotBlank(requestParams)){
                List<RequestParamsField> list = new Gson().fromJson(requestParams, new TypeToken<List<RequestParamsField>>() {
                }.getType());
                List<String> fieldList = list.stream().filter(requestParamsField -> "true".equals(requestParamsField.getRequired())).map(RequestParamsField::getFieldName).sorted().toList();
                List<String> bodyList = new ArrayList<>();
                Objects.requireNonNull(JSON.parseObject(body)).forEach((key, val)->{
                    bodyList.add(key);
                });
                if(bodyList.size() < fieldList.size() || bodyList.stream().anyMatch(str -> !fieldList.contains(str))){
                    throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求参数有误,请查看文档后重新填写");
                }
            }
            return handleResponse(exchange, chain, user, interfaceInfo);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, e.getMessage());
        }
    }

    /**
     * 处理响应
     *
     * @param exchange 交换
     * @param chain    链条
     * @return {@link Mono}<{@link Void}>
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, UserVO user, InterfaceInfo interfaceInfo) {
        ServerHttpResponse originalResponse =exchange.getResponse();
        // 缓存数据的工厂
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 拿到响应码
        HttpStatus statusCode = (HttpStatus) originalResponse.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            // 装饰，增强能力
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                // 等调用完转发的接口后才会执行
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 往返回值里写数据
                        return super.writeWith(
                                fluxBody.handle((dataBuffer, sink) -> {
                                    // 扣除积分

//                                    redissonLockUtil.redissonDistributedLocks(("gateway_" + user.getUserAccount()).intern(), () -> {
//                                        boolean invoke = interfaceInvokeService.invoke(interfaceInfo.getId(), user.getId(), interfaceInfo.getReduceScore());
//                                        if (!invoke) {
//                                            throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败");
//                                        }
//                                    }, "接口调用失败");
                                    try {
                                        synchronized (this){
                                            boolean invoke =interfaceInvokeService.invoke(interfaceInfo.getId(), user.getId(),interfaceInfo.getCost());
                                            if (!invoke) {
                                                sink.error(new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败"));
                                                return;
                                            }
                                        }
                                    } catch (Exception e) {
                                        log.error("invokeCount error", e);
                                    }
                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    // 释放掉内存
                                    DataBufferUtils.release(dataBuffer);
                                    String data = new String(content, StandardCharsets.UTF_8);
                                    // 打印日志
                                    log.info("响应结果：" + data);
                                    sink.next(bufferFactory.wrap(content));
                                }));
                    } else {
                        // 8. 调用失败，返回一个规范的错误码
                        log.error("<--- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            // 设置 response 对象为装饰过的
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        // 降级处理返回数据
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return -1;
    }
}