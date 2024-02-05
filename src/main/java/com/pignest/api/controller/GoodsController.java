package com.pignest.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pignest.api.annotation.AuthCheck;
import com.pignest.api.common.DeleteRequest;
import com.pignest.api.constant.UserConstant;
import com.pignest.api.exception.ThrowUtils;
import com.pignest.api.model.dto.goods.GoodsAddRequest;
import com.pignest.api.model.dto.goods.GoodsQueryRequest;
import com.pignest.api.model.dto.goods.GoodsUpdateRequest;
import com.pignest.api.model.entity.Goods;
import com.pignest.api.service.GoodsService;
import com.pignest.api.service.UserService;
import com.pignest.api_common.common.BaseResponse;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.common.ResultUtils;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_common.model.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author went
 * @date 2024/1/11 15:36
 **/
@RestController
@RequestMapping("/api/goods")
@Slf4j
public class GoodsController {


    @Resource
    GoodsService goodsService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param topUpAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addGoods(@RequestBody GoodsAddRequest topUpAddRequest, HttpServletRequest request) {
        if (topUpAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Goods goods = new Goods();
        if(StringUtils.isBlank(topUpAddRequest.getAvatarUrl())){
            goods.setAvatarUrl("https://apply-1308809307.cos.ap-nanjing.myqcloud.com/top_up_avatar/%E6%9A%82%E6%97%A0%E6%95%B0%E6%8D%AE.png");
        }
        BeanUtils.copyProperties(topUpAddRequest, goods);
        goodsService.validGoods(goods, true);
        User loginUser = userService.getLoginUser(request);
        goods.setCreateId(loginUser.getId());
        boolean result = goodsService.save(goods);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long id = goods.getId();
        return ResultUtils.success(id);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteGoods(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Goods goods = goodsService.getById(id);
        ThrowUtils.throwIf(goods == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!goods.getCreateId().equals(user.getId()) && userService.isNotAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = goodsService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param goodsUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateGoods(@RequestBody GoodsUpdateRequest goodsUpdateRequest) {
        if (goodsUpdateRequest == null || goodsUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsUpdateRequest, goods);
        // 参数校验
        goodsService.validGoods(goods, false);
        long id = goodsUpdateRequest.getId();
        // 判断是否存在
        Goods oldGoods = goodsService.getById(id);
        ThrowUtils.throwIf(oldGoods == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = goodsService.updateById(goods);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Goods> getGoodsById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Goods goods = goodsService.getById(id);
        return ResultUtils.success(goods);

    }



    /**
     * 分页获取接口列表
     *
     * @param goodsQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Goods>> listGoodsByPage(@RequestBody GoodsQueryRequest goodsQueryRequest,
                                                     HttpServletRequest request) {
        long current = goodsQueryRequest.getCurrent();
        long size = goodsQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Goods> topUpPage = goodsService.page(new Page<>(current, size),
                goodsService.getQueryWrapper(goodsQueryRequest));
        return ResultUtils.success(topUpPage);
    }
}
