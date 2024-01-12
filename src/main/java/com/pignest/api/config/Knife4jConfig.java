package com.pignest.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Knife4j 接口文档配置
 * https://doc.xiaominfo.com/knife4j/documentation/get_start.html
 *

 */
@Configuration

@Profile({"dev", "test"})
public class Knife4jConfig {

//    @Bean
//    public Docket defaultApi2() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        .title("接口文档")
//                        .description("API_BackEnd")
//                        .version("1.0")
//                        .build())
//                .select()
//                // 指定 Controller 扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.pignest.api.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
}