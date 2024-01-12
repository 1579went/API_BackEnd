package com.pignest.api_interface;

import com.pignest.api_client_sdk.service.impl.ApiServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiServiceApplicationTests {
    @Resource
    private ApiServiceImpl apiClient;

    @Test
    void contextLoads() {
        apiClient.getKey();
    }

}
