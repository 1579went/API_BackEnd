package com.pignest.api_interface.service;

import com.pignest.api_interface.model.WeatherMap;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pignest.api_interface.model.dto.WeatherRequest;

/**
* @author Black
* @description 针对表【weather_map(天气查询地图表)】的数据库操作Service
* @createDate 2024-01-17 16:54:39
*/
public interface WeatherMapService extends IService<WeatherMap> {
    Object getWeatherReport(String area,String extensions);

}
