package com.emergency.emergencyroom.DTO.request;

import java.time.LocalDateTime;

public record DeathRequest(String deathCause, LocalDateTime deathTime) {}
