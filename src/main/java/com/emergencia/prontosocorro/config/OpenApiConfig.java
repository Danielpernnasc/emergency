package com.emergencia.prontosocorro.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Profile("!test")
@Configuration
public class OpenApiConfig {
  @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://spare-shae-danielpernnasc-3c2dab9e.koyeb.app")
                ));
    }
}
