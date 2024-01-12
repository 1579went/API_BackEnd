package com.pignest.api_interface.util;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author went
 * @date 2023/12/27 14:59
 **/
public class util {
    public static String enCodeStr(String str){
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }
}
