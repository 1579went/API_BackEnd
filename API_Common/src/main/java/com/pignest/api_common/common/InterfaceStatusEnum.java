package com.pignest.api_common.common;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口状态枚举
 *
 * @author Black
 */
@Getter
public enum InterfaceStatusEnum {

    /**
     * 在线
     */
    ONLINE("开启", 1),
    /**
     * 离线
     */
    OFFLINE("关闭", 2),

    /**
     * 离线
     */
    AUDITING("审核中", 0);

    private final String text;

    private final int value;

    InterfaceStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值
     *
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }


}