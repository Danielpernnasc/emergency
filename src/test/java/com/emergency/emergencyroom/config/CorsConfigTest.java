package com.emergency.emergencyroom.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.web.filter.CorsFilter;


class CorsConfigTest {

  @Test
  void shouldCreateCorsFilter() {

      CorsConfig config = new CorsConfig();

      try {
          var prodField = CorsConfig.class.getDeclaredField("prodUrl");
          prodField.setAccessible(true);
          prodField.set(config, "https://prod.com");

          var localField = CorsConfig.class.getDeclaredField("localUrl");
          localField.setAccessible(true);
          localField.set(config, "http://localhost:8080");

      } catch (Exception e) {
          fail("Erro ao setar campos: " + e.getMessage());
      }

      CorsFilter filter = config.corsFilter();

      assertNotNull(filter);
  }
}