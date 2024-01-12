package com.pignest.api_client_sdk.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.pignest.api_client_sdk.client.API_Client;
import com.pignest.api_client_sdk.service.ApiService;
import com.pignest.api_client_sdk.utils.SignUtils;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.pignest.api_client_sdk.constant.SignConstant.*;

/**
 * @author went
 * @date 2023/12/20 20:30
 **/
@Data
@Accessors(chain = true)
@Service
public class ApiServiceImpl implements ApiService {


    private API_Client apiClient;

    private String url;

    private Map<String,Object> requestParams = new HashMap<>();

    public ApiServiceImpl(API_Client apiClient) {
        this.apiClient = apiClient;
    }

    public void addParams(String fieldName, Object value){
        this.requestParams.put(fieldName,value);
    }

    public String getKey() {
        return apiClient.getAccessKey() + "-" + apiClient.getSecretKey();
    }

    /**
     * @description post请求
     * @return java.lang.String
     * @author Black
     * @date 2023/12/18 18:29
     */
    @Override
    public String post(){
        String accessKey = apiClient.getAccessKey();
        String secretKey = apiClient.getSecretKey();
        if(StringUtils.isAnyBlank(accessKey,secretKey,url)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空！");
        }
        HttpResponse httpResponse = HttpRequest.post(url)
                .addHeaders(getHeaderMap(apiClient))
                .body(new JSONObject(requestParams).toString())
                .execute();

        return httpResponse.body();
    }
    /**
     * @description get请求
     * @return java.lang.String
     * @author Black
     * @date 2023/12/18 18:29
     */
    @Override
    public String get(){
        String accessKey = apiClient.getAccessKey();
        String secretKey = apiClient.getSecretKey();
        if(StringUtils.isAnyBlank(accessKey,secretKey,url)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空！");
        }
        StringBuilder urlBuilder = new StringBuilder();
        // urlBuilder最后是/结尾且path以/开头的情况下，去掉urlBuilder结尾的/
        if (urlBuilder.toString().endsWith("/") && url.startsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append(url);
        if (!requestParams.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }

        HttpResponse httpResponse = HttpRequest.get(urlBuilder.toString())
                .addHeaders(getHeaderMap(apiClient))
                .execute();
        return httpResponse.body();
    }

    @Override
    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8090/api/sayHi/", paramMap);
        System.out.println(result);
        return result;
    }

    /**
     * @description
     * @param apiClient
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @author Black
     * @date 2023/12/18 18:38
     */
    private Map<String, String> getHeaderMap(API_Client apiClient) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(ACCESSKEY, apiClient.getAccessKey());
        // 一定不能直接发送
        //hashMap.put("secretKey", secretKey);
        hashMap.put(NONCE, SignUtils.getNonceStr(7));
        hashMap.put(BODY, enCodeRequestParams(requestParams));
        hashMap.put(TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put(SIGN, SignUtils.getSign(hashMap,apiClient.getSecretKey()));
        return hashMap;
    }

    public String enCodeRequestParams(Map<String,Object> requestParams){
        requestParams.entrySet().stream().forEach((entry) -> {
            this.requestParams.put(entry.getKey(), URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        });
        return new JSONObject(this.requestParams).toString();
    }
}
