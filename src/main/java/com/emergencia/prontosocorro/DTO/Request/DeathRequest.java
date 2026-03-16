package com.emergencia.prontosocorro.DTO.Request;

import java.time.LocalDateTime;

public record DeathRequest(String deathCause, LocalDateTime deathTime) {}
