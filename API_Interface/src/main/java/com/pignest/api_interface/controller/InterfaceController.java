package com.pignest.api_interface.controller;

import com.pignest.api_client_sdk.service.impl.ApiServiceImpl;
import com.pignest.api_common.common.BaseResponse;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.common.ResultUtils;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_interface.model.sayWhat;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.pignest.api_interface.util.util.enCodeStr;

/**
 * @author Black
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class InterfaceController {

    @Resource
    private ApiServiceImpl apiService;

    @GetMapping("/say")
    public String say() {
        return apiService.getKey();
    }

    @GetMapping("/sayHi")
    public BaseResponse<String> sayHi(String name) {
        log.info("Hi~" + (name == null ? "" : name));
        return ResultUtils.success( "Hi~" + (name == null ? "" : name));
    }

    @PostMapping("/sayWhat")
    public BaseResponse<String> sayWhat(@RequestBody sayWhat saying) {
        String name = enCodeStr(saying.getName());
        String words = enCodeStr(saying.getWords());
        if(StringUtils.isAnyBlank(name,words)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空！");
        }
        return ResultUtils.success(name + " say:" + words);
    }

}