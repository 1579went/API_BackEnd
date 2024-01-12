package com.pignest.api_interface.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author went
 * @date 2023/12/26 18:14
 **/
@Data
public class sayWhat implements Serializable {
    @Serial
    private static final long serialVersionUID = 2878008686007027516L;
    private String name;
    private String words;
}
