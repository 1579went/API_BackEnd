package com.pignest.api_common.model.dto;

import lombok.Data;

/**
 * @author Black
 */
@Data
public class ResponseParamsField {
    private String id;
    private String fieldName;
    private String value;
    private String type;
    private String description;
}