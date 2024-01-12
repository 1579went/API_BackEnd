package com.pignest.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pignest.api.annotation.AuthCheck;
import com.pignest.api.common.DeleteRequest;
import com.pignest.api.constant.UserConstant;
import com.pignest.api.exception.ThrowUtils;
import com.pignest.api.model.dto.topUp.TopUpAddRequest;
import com.pignest.api.model.dto.topUp.TopUpQueryRequest;
import com.pignest.api.model.dto.topUp.TopUpUpdateRequest;
import com.pignest.api.model.entity.TopUp;
import com.pignest.api.service.TopUpService;
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
@RequestMapping("/api/topUp")
@Slf4j
public class TopUpController {


    @Resource
    TopUpService topUpService;

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
    public BaseResponse<Long> addTopUp(@RequestBody TopUpAddRequest topUpAddRequest, HttpServletRequest request) {
        if (topUpAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TopUp topUp = new TopUp();
        if(StringUtils.isBlank(topUpAddRequest.getAvatarUrl())){
            topUp.setAvatarUrl("https://apply-1308809307.cos.ap-nanjing.myqcloud.com/top_up_avatar/%E6%9A%82%E6%97%A0%E6%95%B0%E6%8D%AE.png");
        }
        BeanUtils.copyProperties(topUpAddRequest, topUp);
        topUpService.validTopUp(topUp, true);
        User loginUser = userService.getLoginUser(request);
        topUp.setCreateId(loginUser.getId());
        boolean result = topUpService.save(topUp);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long id = topUp.getId();
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
    public BaseResponse<Boolean> deleteTopUp(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        TopUp topUp = topUpService.getById(id);
        ThrowUtils.throwIf(topUp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!topUp.getCreateId().equals(user.getId()) && userService.isNotAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = topUpService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param topUpUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateTopUp(@RequestBody TopUpUpdateRequest topUpUpdateRequest) {
        if (topUpUpdateRequest == null || topUpUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TopUp topUp = new TopUp();
        BeanUtils.copyProperties(topUpUpdateRequest, topUp);
        // 参数校验
        topUpService.validTopUp(topUp, false);
        long id = topUpUpdateRequest.getId();
        // 判断是否存在
        TopUp oldTopUp = topUpService.getById(id);
        ThrowUtils.throwIf(oldTopUp == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = topUpService.updateById(topUp);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<TopUp> getTopUpById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        TopUp topUp = topUpService.getById(id);
        return ResultUtils.success(topUp);

    }



    /**
     * 分页获取接口列表
     *
     * @param topUpQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<TopUp>> listTopUpByPage(@RequestBody TopUpQueryRequest topUpQueryRequest,
                                                                     HttpServletRequest request) {
        long current = topUpQueryRequest.getCurrent();
        long size = topUpQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<TopUp> topUpPage = topUpService.page(new Page<>(current, size),
                topUpService.getQueryWrapper(topUpQueryRequest));
        return ResultUtils.success(topUpPage);
    }
}
