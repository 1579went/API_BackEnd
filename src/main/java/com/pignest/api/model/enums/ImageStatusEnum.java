package com.pignest.api.model.enums;

import lombok.Getter;

/**
 * 错误码
 *
 * @author Black
 */
@Getter
public enum ImageStatusEnum {
    /**
     * 成功
     */
    SUCCESS("success", "done"),
    /**
     * 参数错误
     */
    ERROR("error", "error");


    /**
     * 状态
     */
    private final String status;

    /**
     * 信息
     */
    private final String value;

    ImageStatusEnum(String status, String value) {
        this.status = status;
        this.value = value;
    }

}
