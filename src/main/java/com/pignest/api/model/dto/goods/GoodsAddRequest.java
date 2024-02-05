package com.pignest.api.model.dto.goods;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author went
 * @date 2024/1/11 15:42
 **/
@Data
public class GoodsAddRequest implements Serializable {

    /**
     * 充值项目名
     */
    private String name;

    /**
     * 充值项目图片
     */
    private String avatarUrl;

    /**
     * 价格
     */
    private Long price;

    /**
     * 猪币
     */
    private Long pigCoins;

    /**
     * 充值项目描述
     */
    private String description;

    @Serial
    private static final long serialVersionUID = -3906909286963563992L;
}
