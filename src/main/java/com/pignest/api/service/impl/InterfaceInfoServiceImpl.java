package com.pignest.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api.mapper.InterfaceInfoMapper;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoAddRequest;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoSearchRequest;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_common.model.dto.RequestParamsField;
import com.pignest.api_common.model.dto.ResponseParamsField;
import com.pignest.api_common.model.entity.InterfaceInfo;
import com.pignest.api.service.InterfaceInfoService;
import com.pignest.api.utils.SqlUtils;
import com.pignest.api.constant.CommonConstant;
import com.pignest.api.exception.ThrowUtils;
import com.pignest.api_common.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Black
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-11-09 17:52:01
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestParams = interfaceInfo.getRequestParams();
        String method = interfaceInfo.getMethod();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(
                    name, description, url,requestParams,method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 200) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口描述过长");
        }
    }

    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryRequest == null) {
            return queryWrapper;
        }
        Long id = interfaceInfoQueryRequest.getId();
        String name = interfaceInfoQueryRequest.getName();
        String url = interfaceInfoQueryRequest.getUrl();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .like(StringUtils.isNotBlank(url), "url", url)
                .eq(ObjectUtils.isNotEmpty(id), "id", id)
                .eq("is_delete", false)
                .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public QueryWrapper<InterfaceInfo> getSearchWrapper(InterfaceInfoSearchRequest interfaceInfoSearchRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoSearchRequest == null) {
            return queryWrapper;
        }
        String searchText = interfaceInfoSearchRequest.getSearchText();
        String sortField = interfaceInfoSearchRequest.getSortField();
        String sortOrder = interfaceInfoSearchRequest.getSortOrder();
        if(StringUtils.isNotBlank(searchText)){
            queryWrapper.like(StringUtils.isNotBlank(searchText), "name", searchText)
                    .or()
                    .like(StringUtils.isNotBlank(searchText), "url", searchText)
                    .or()
                    .like(StringUtils.isNotBlank(searchText), "description", searchText)
                    .eq("is_delete", false)
                    .eq("status", 1)
                    .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                            sortField);
        }else {
            queryWrapper
                    .eq("is_delete", false)
                    .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);

        }
        return queryWrapper;
    }
}




