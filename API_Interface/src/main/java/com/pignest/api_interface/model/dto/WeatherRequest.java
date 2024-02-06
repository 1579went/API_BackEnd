package com.pignest.api_interface.model.dto;

import lombok.Data;

/**
 * @author went
 * @date 2024/1/17 17:14
 **/
@Data
public class WeatherRequest {
    /**
     * 地区名称
     */
    private String area;
    /**
     *    气象类型
     *    可选值：base/all
     *    base:返回实况天气
     *    all:返回预报天气
     */
    private String extensions;
}
