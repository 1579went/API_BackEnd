package com.pignest.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author Black

 */
@Data
public class IDRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}