package com.emergency.emergencyRoom.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.swagger.v3.oas.models.OpenAPI;

class OpenApiConfigTest {
     @Test
    void shouldCreateOpenAPIWithServers() {
        OpenApiConfig config = new OpenApiConfig();

        // simula valores (já que @Value não funciona fora do Spring)
        try {
            var prodField = OpenApiConfig.class.getDeclaredField("prodUrl");
            prodField.setAccessible(true);
            prodField.set(config, "https://prod.com");

            var localField = OpenApiConfig.class.getDeclaredField("localUrl");
            localField.setAccessible(true);
            localField.set(config, "http://localhost:8080");

        } catch (Exception e) {
            fail("Erro ao setar campos: " + e.getMessage());
        }

        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getServers());
        assertEquals(2, openAPI.getServers().size());

        assertEquals("https://prod.com", openAPI.getServers().get(0).getUrl());
        assertEquals("http://localhost:8080", openAPI.getServers().get(1).getUrl());
    }

}
