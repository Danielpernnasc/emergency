package com.emergency.emergencyRoom.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Profile("!test")
@Configuration
public class OpenApiConfig {

   @Value("${app.url.prod}")
    private String prodUrl;

    @Value("${app.url.local}")
    private String localUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url(prodUrl),
                        new Server().url(localUrl)
                ));
    }
}