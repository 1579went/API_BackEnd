package com.pignest.api.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新个人信息请求
 *
 * @author Black

 */
@Data
public class UserUpdateStatusRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户角色（user->用户,admin->管理员,ban->封号）
     */
    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}