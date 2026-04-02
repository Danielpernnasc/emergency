package com.emergency.emergencyRoom.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.web.filter.CorsFilter;

import com.emergency.emergencyRoom.config.CorsConfig;

class CorsConfigTest {


  @Test
    void shouldCreateCorsFilter() {

        CorsConfig config = new CorsConfig();

        CorsFilter filter = config.corsFilter();

        assertNotNull(filter);
        assertNotNull(filter.getClass());

        


    }
}