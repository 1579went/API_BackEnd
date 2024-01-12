package com.pignest.api;

import com.pignest.api_client_sdk.service.ApiService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author went
 * @date 2023/12/20 17:35
 **/
@SpringBootTest
public class APIClientTest {
    @Resource
    private ApiService apiService;

    @Test
    void contextLoads() {
        apiService.getNameByGet("went");
    }

}
