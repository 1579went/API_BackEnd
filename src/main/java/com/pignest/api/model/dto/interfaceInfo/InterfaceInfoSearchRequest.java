package com.pignest.api.model.dto.interfaceInfo;

import com.pignest.api.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询请求
 *
 * @author Black

 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoSearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索内容
     */
    private String searchText;

    @Serial
    private static final long serialVersionUID = 1L;
}