package com.pignest.api.model.dto.interfaceInfo;

import com.pignest.api_common.model.dto.RequestParamsField;
import com.pignest.api_common.model.dto.ResponseParamsField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @author Black

 */
@Data
public class InterfaceInfoAddRequest implements Serializable {


    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private List<RequestParamsField> requestParams;

    /**
     * 响应参数
     */
    private List<ResponseParamsField> responseParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;


    /**
     * 请求类型
     */
    private String method;

    /**
     * 花费
     */
    private Integer cost;

    /**
     * 接口头像
     */
    private String avatarUrl;


    @Serial
    private static final long serialVersionUID = 1L;
}