package com.emergencia.prontosocorro.Controller.DTO.Request;


import java.time.LocalDateTime;

import com.emergencia.prontosocorro.Domain.models.StatusType;

public record StatePatientRequest(StatusType statusType, String justification, LocalDateTime date) {
    public StatePatientRequest() {
        this(null, null, null);
    }
}
