package com.pignest.api_interface.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api_common.common.BaseResponse;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.common.ResultUtils;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_interface.model.ThirdPartyInterface;
import com.pignest.api_interface.service.ThirdPartyInterfaceService;
import com.pignest.api_interface.mapper.ThirdPartyInterfaceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.pignest.api_interface.exception.ThrowUtils.throwIf;

/**
* @author Black
* @description 针对表【third_party_interface(第三方接口信息)】的数据库操作Service实现
* @createDate 2024-02-24 16:58:19
*/
@Service
public class ThirdPartyInterfaceServiceImpl extends ServiceImpl<ThirdPartyInterfaceMapper, ThirdPartyInterface>
    implements ThirdPartyInterfaceService{
    @Override
    public BaseResponse<Object> query(String name , String query) {
        ThirdPartyInterface thirdPartyInterface = getOne(new QueryWrapper<ThirdPartyInterface>().eq("name",name));
        throwIf(null == thirdPartyInterface,new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        Map<String,Object> params = new HashMap<>();
        params.put("key", thirdPartyInterface.getAccessKey());
        if(Objects.equals(thirdPartyInterface.getName(), "constellation")){
            params.put("keyword",query);
        }else{
            params.put("wd",query);
        }
        String httpResponse = HttpUtil.get(thirdPartyInterface.getPath(),params);
        JSONObject jsonObject = JSONUtil.parseObj(httpResponse);
        if(jsonObject.containsKey("result")){
            Object result = jsonObject.get("result");
            if(Objects.isNull(result)|| Objects.equals(result.toString(), "null")){
                if(jsonObject.containsKey("reason") && !Objects.isNull(jsonObject.get("reason"))){
                    return ResultUtils.error(jsonObject.get("error_code"),jsonObject.get("reason").toString()) ;
                }
            }
            return ResultUtils.success(jsonObject.get("result"));
        }
        return ResultUtils.success(jsonObject);
    }
}




