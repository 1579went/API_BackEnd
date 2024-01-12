package com.pignest.api.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author Black
 */
@Data
public class InterfaceInfoUpdateAvatarRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5237950468710940348L;
    private long id;
    /**
     * 接口头像
     */
    private String avatarUrl;
}
