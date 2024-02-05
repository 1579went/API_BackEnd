package com.pignest.api.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author Black
 */
@Data
public class ImageVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4296258656223039373L;
    private String uid;
    private String name;
    private String status;
    private String url;
}