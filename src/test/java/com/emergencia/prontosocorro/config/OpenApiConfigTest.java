package com.emergencia.prontosocorro.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import io.swagger.v3.oas.models.OpenAPI;


public class OpenApiConfigTest {

    @Test
    void testCustomOpenAPI() throws Exception {

        OpenApiConfig config = new OpenApiConfig();

        // injeta valores manualmente
        var localField = OpenApiConfig.class.getDeclaredField("localUrl");
        localField.setAccessible(true);
        localField.set(config, "http://localhost:8080");

        var prodField = OpenApiConfig.class.getDeclaredField("prodUrl");
        prodField.setAccessible(true);
        prodField.set(config, "https://spare-shae-danielpernnasc-3c2dab9e.koyeb.app");

        OpenAPI openAPI = config.customOpenAPI();

        assertEquals(2, openAPI.getServers().size());
        assertEquals("http://localhost:8080", openAPI.getServers().get(0).getUrl());
        assertEquals("https://spare-shae-danielpernnasc-3c2dab9e.koyeb.app", openAPI.getServers().get(1).getUrl());
    }
}

