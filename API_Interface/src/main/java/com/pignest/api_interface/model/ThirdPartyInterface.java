package com.pignest.api_interface.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 第三方接口信息
 * @author Black
 * @TableName third_party_interface
 */
@TableName(value ="third_party_interface")
@Data
public class ThirdPartyInterface implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 访问秘钥
     */
    private String accessKey;

    /**
     * 路径
     */
    private String path;

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