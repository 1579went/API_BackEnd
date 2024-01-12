package com.pignest.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoSearchRequest;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.pignest.api_common.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Black
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-11-09 17:52:01
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoSearchRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getSearchWrapper(InterfaceInfoSearchRequest interfaceInfoSearchRequest);
}
