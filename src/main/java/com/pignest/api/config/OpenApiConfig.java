package com.pignest.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "My API", version = "v1"),
        servers = @Server(url = "http://localhost:8101/")
)
@Configuration
public class OpenApiConfig {
    // ...
}