package com.pignest.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.pignest.api.model.dto.topUp.TopUpQueryRequest;
import com.pignest.api.model.entity.TopUp;
import com.pignest.api_common.model.entity.InterfaceInfo;


/**
* @author Black
* @description 针对表【top_up(充值项目)】的数据库操作Service
* @createDate 2024-01-11 14:21:51
*/
public interface TopUpService extends IService<TopUp> {
    /**
     * 校验
     *
     * @param topUp
     * @param add
     */
    void validTopUp(TopUp topUp, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<TopUp> getQueryWrapper(TopUpQueryRequest topUpQueryRequest);

}
