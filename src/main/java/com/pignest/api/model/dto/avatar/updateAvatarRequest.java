package com.pignest.api.model.dto.avatar;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *

 */
@Data
public class updateAvatarRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户头像地址
     */
    private String avatarUrl;

    private static final long serialVersionUID = 1L;
}