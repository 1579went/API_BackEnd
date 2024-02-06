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
 * 天气查询地图表
 * @author Black
 * @TableName weather_map
 */
@TableName(value ="weather_map")
@Data
public class WeatherMap implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 区域编码
     */
    private Integer adCode;

    /**
     * 区域中文名
     */
    private String area;

    /**
     * 城市编码
     */
    private Integer cityCode;

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