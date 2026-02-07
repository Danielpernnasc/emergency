package com.emergencia.prontosocorro.Controller.DTO.Response;

import java.time.LocalDateTime;

import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

public record FirstCareResponse(
    Long id,
    CareStatus careStatus,
    SpecialistMedic specialistMedic,
    LocalDateTime careDateTime,

    Long peopleId,
    String peopleName,

    Long hospitalId,
    String hospitalName
    
) {

    public FirstCareResponse() {
        this(null, null, null, null, null, null, null, null);
    }
}
