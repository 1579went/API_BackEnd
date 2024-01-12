package com.pignest.api.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 充值项目
 * @author Black
 * @TableName top_up
 */
@TableName(value ="top_up")
@Data
public class TopUp implements Serializable {
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
    private Double price;

    /**
     * 充值项目描述
     */
    private String description;

    /**
     * 是否删除（0->正常，1->删除）
     */
    private Integer isDelete;

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
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}