package com.pignest.api_client_sdk.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.pignest.api_client_sdk.utils.SignUtils;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.pignest.api_client_sdk.constant.SignConstant.*;


/**
 * @author went
 * @date 2023/11/23 17:18
 **/
@Data
@AllArgsConstructor
public class API_Client {

    private String accessKey;

    private String secretKey;


}
