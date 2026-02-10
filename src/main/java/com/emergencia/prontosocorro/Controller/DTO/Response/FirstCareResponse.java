package com.emergencia.prontosocorro.Controller.DTO.Response;

import java.time.LocalDateTime;
import java.util.List;

import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

public record FirstCareResponse(
    Long id,
    CareStatus careStatus,
    SpecialistMedic specialistMedic,
    LocalDateTime careDateTime,
    List<ComorbidityType> comorbidities,
    Long peopleId,
    String peopleName,
    Long hospitalId,
    String hospitalName
) {
}
