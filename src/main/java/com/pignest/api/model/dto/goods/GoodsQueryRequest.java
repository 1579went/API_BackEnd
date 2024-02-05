package com.pignest.api.model.dto.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pignest.api.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author Black

 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsQueryRequest extends PageRequest implements Serializable {

    /**
     * 充值项目编号
     */
    @TableId(type = IdType.AUTO)
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
    private Long price;

    /**
     * 充值项目描述
     */
    private String description;

    /**
     * 是否删除（0->正常，1->删除）
     */
    private Integer idDelete;

    /**
     * 创建者ID
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;



    @Serial
    private static final long serialVersionUID = 1L;
}