package com.pignest.api_interface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Black
 */
@SpringBootApplication(scanBasePackages = {"com.pignest"})
@MapperScan("com.pignest.api_interface.mapper")
public class ApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiInterfaceApplication.class, args);
    }

}
