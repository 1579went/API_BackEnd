package com.pignest.api_interface.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_interface.model.WeatherMap;
import com.pignest.api_interface.mapper.WeatherMapMapper;

import com.pignest.api_interface.service.WeatherMapService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author Black
* @description 针对表【weather_map(天气查询地图表)】的数据库操作Service实现
* @createDate 2024-01-17 16:54:39
*/
@Service
public class WeatherMapServiceImpl extends ServiceImpl<WeatherMapMapper, WeatherMap>
    implements WeatherMapService {

    @Value("${weather.apiKey}")
    private String apiKey;

    @Override
    public Object getWeatherReport(String area,String extensions){
        if(StringUtils.isAnyBlank(area,extensions)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        WeatherMap weatherMap = query().eq("area",area).one();
        if(null == weatherMap){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"找不到该地区，请检查地区名是否有误，是否漏了‘省’，‘市’，‘区’等。");
        }
        int adCode = weatherMap.getAdCode();
        Map<String,Object> params = new HashMap<>();
        params.put("key",apiKey);
        params.put("city",adCode);
        params.put("extensions",extensions);
        String url = "https://restapi.amap.com/v3/weather/weatherInfo";
        String httpResponse = HttpUtil.get(url,params);
        JSONObject jsonObject = JSONUtil.parseObj(httpResponse);
        if("1".equals(jsonObject.get("status"))){
            return jsonObject;
        }else{
            return "";
        }
    }
}




