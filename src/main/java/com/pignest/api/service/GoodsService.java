package com.pignest.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pignest.api.model.dto.goods.GoodsQueryRequest;
import com.pignest.api.model.entity.Goods;


/**
* @author Black
* @description 针对表【top_up(充值项目)】的数据库操作Service
* @createDate 2024-01-11 14:21:51
*/
public interface GoodsService extends IService<Goods> {
    /**
     * 校验
     *
     * @param goods
     * @param add
     */
    void validGoods(Goods goods, boolean add);

    /**
     * 获取查询条件
     *
     * @param goodsQueryRequest
     * @return
     */
    QueryWrapper<Goods> getQueryWrapper(GoodsQueryRequest goodsQueryRequest);

}
