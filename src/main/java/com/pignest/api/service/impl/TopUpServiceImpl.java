package com.pignest.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api.constant.CommonConstant;
import com.pignest.api.exception.ThrowUtils;
import com.pignest.api.mapper.TopUpMapper;
import com.pignest.api.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.pignest.api.model.dto.topUp.TopUpQueryRequest;
import com.pignest.api.model.entity.TopUp;
import com.pignest.api.service.TopUpService;
import com.pignest.api.utils.SqlUtils;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_common.model.entity.InterfaceInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Black
* @description 针对表【top_up(充值项目)】的数据库操作Service实现
* @createDate 2024-01-11 14:21:51
*/
@Service
public class TopUpServiceImpl extends ServiceImpl<TopUpMapper, TopUp>
    implements TopUpService {
    @Override
    public void validTopUp(TopUp topUp, boolean add) {
        if (topUp == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        String name = topUp.getName();
        Double price = topUp.getPrice();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(
                    name)||price<0, ErrorCode.PARAMS_ERROR);
        }

        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口名称过长");
        }
    }

    @Override
    public QueryWrapper<TopUp> getQueryWrapper(TopUpQueryRequest topUpQueryRequest) {
        QueryWrapper<TopUp> queryWrapper = new QueryWrapper<>();
        if (topUpQueryRequest == null) {
            return queryWrapper;
        }
        Long id = topUpQueryRequest.getId();
        String name = topUpQueryRequest.getName();
        String sortField = topUpQueryRequest.getSortField();
        String sortOrder = topUpQueryRequest.getSortOrder();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .eq(ObjectUtils.isNotEmpty(id), "id", id)
                .eq("is_delete", false)
                .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




