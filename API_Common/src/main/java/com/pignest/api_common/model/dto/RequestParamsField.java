package com.pignest.api_common.model.dto;

import lombok.Data;


/**
 * @author Black
 */
@Data
public class RequestParamsField {
    private String id;
    private String fieldName;
    private String type;
    private String description;
    private String value;
    private String required;
}