package com.pignest.api_interface.service;

import com.pignest.api_common.common.BaseResponse;
import com.pignest.api_interface.model.ThirdPartyInterface;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Black
* @description 针对表【third_party_interface(第三方接口信息)】的数据库操作Service
* @createDate 2024-02-24 16:58:19
*/
public interface ThirdPartyInterfaceService extends IService<ThirdPartyInterface> {
    BaseResponse<Object> query(String name, String query);
}
