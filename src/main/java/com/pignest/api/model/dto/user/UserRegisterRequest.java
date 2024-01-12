package com.pignest.api.model.dto.user;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户注册请求体
 *
 * @author Black

 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
