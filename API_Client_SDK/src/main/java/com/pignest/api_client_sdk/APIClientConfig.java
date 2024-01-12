package com.pignest.api_client_sdk;

import com.pignest.api_client_sdk.client.API_Client;
import com.pignest.api_client_sdk.service.impl.ApiServiceImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author went
 * @date 2023/11/23 17:39
 **/
@Data
@Configuration
@ComponentScan
//@EnableConfigurationProperties(API_Client.class)
@ConfigurationProperties("api.client")
public class APIClientConfig {

    private String accessKey;

    private String secretKey;


    @Bean
    public API_Client apiClient(){
        return new API_Client(accessKey, secretKey);
    }
    @Bean
    public ApiServiceImpl apiServiceImpl(){
        return new ApiServiceImpl(apiClient());
    }


}
