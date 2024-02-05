package com.pignest.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api.constant.CommonConstant;
import com.pignest.api.exception.ThrowUtils;
import com.pignest.api.mapper.GoodsMapper;
import com.pignest.api.model.dto.goods.GoodsQueryRequest;
import com.pignest.api.model.entity.Goods;
import com.pignest.api.service.GoodsService;
import com.pignest.api.utils.SqlUtils;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Black
* @description 针对表【top_up(充值项目)】的数据库操作Service实现
* @createDate 2024-01-11 14:21:51
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {
    @Override
    public void validGoods(Goods goods, boolean add) {
        if (goods == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        String name = goods.getName();
        Long price = goods.getPrice();
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
    public QueryWrapper<Goods> getQueryWrapper(GoodsQueryRequest goodsQueryRequest) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goodsQueryRequest == null) {
            return queryWrapper;
        }
        Long id = goodsQueryRequest.getId();
        String name = goodsQueryRequest.getName();
        String sortField = goodsQueryRequest.getSortField();
        String sortOrder = goodsQueryRequest.getSortOrder();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .eq(ObjectUtils.isNotEmpty(id), "id", id)
                .eq("is_delete", false)
                .orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }
}




