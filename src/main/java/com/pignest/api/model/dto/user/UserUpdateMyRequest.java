package com.pignest.api.model.dto.user;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户更新个人信息请求
 *
 * @author Black

 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像地址
     */
    private String avatarUrl;


    /**
     * 性别（0->女,1->男）
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;


    @Serial
    private static final long serialVersionUID = 1L;
}