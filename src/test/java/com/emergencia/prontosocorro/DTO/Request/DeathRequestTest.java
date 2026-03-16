package com.emergencia.prontosocorro.DTO.Request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeathRequestTest {

    @Test
    void shouldDeathRequest(){

        LocalDateTime deathTime =  LocalDateTime.now();

        DeathRequest request = new DeathRequest(
            "Infarto",
            deathTime
        );

        assertEquals("Infarto", request.deathCause());
        assertEquals(deathTime, request.deathTime());

        
        
    }


}
