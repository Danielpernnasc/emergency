package com.emergencia.prontosocorro.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.web.filter.CorsFilter;

class CorsConfigTest {


  @Test
    void shouldCreateCorsFilter() {

        CorsConfig config = new CorsConfig();

        CorsFilter filter = config.corsFilter();

        assertNotNull(filter);

        


    }
}