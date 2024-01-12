package com.pignest.api_client_sdk.service;

import com.pignest.api_client_sdk.client.API_Client;

/**
 * @author went
 * @date 2023/12/20 20:29
 **/
public interface ApiService {
    String get();
    String post();

    String getNameByGet(String name);
}
