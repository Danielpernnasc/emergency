package com.emergencia.prontosocorro.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.swagger.v3.oas.models.OpenAPI;

public class OpenApiConfigTest {

    @Test
    void shouldCreateOpenApiWithBothServers() throws Exception {

        // Arrange
        OpenApiConfig config = new OpenApiConfig();

        var prodField = OpenApiConfig.class.getDeclaredField("prodUrl");
        prodField.setAccessible(true);
        prodField.set(config, "https://spare-shae-danielpernnasc-3c2dab9e.koyeb.app");

        var localField = OpenApiConfig.class.getDeclaredField("localUrl");
        localField.setAccessible(true);
        localField.set(config, "http://localhost:8080");

        // Act
        OpenAPI openAPI = config.customOpenAPI();

        // Assert
        assertNotNull(openAPI);
        assertNotNull(openAPI.getServers());
        assertEquals(2, openAPI.getServers().size());

        List<String> urls = openAPI.getServers()
                .stream()
                .map(server -> server.getUrl())
                .toList();

        assertTrue(urls.contains("https://spare-shae-danielpernnasc-3c2dab9e.koyeb.app"));
        assertTrue(urls.contains("http://localhost:8080"));
    }
}