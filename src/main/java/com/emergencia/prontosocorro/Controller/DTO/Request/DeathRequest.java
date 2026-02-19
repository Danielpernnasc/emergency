package com.emergencia.prontosocorro.Controller.DTO.Request;

import java.time.LocalDateTime;

public record DeathRequest(String deathCause, LocalDateTime deathTime) {

    public LocalDateTime deathTime() {
        return LocalDateTime.now();
    }

}
