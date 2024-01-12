package com.pignest.api.model.dto.topUp;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author went
 * @date 2024/1/11 15:42
 **/
@Data
public class TopUpUpdateRequest implements Serializable {

    /**
     * 充值项目编号
     */
    private Long id;

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
    private Double price;

    /**
     * 充值项目描述
     */
    private String description;

    @Serial
    private static final long serialVersionUID = 3913480262315382561L;
}
