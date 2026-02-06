package com.emergencia.prontosocorro.Controller.DTO;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;


public record FirstCareRequest(
    Long peopleId,
    Long hospitalId,
    SpecialistMedic specialistMedic
) {
    public FirstCareRequest() {
        this(null, null, null);
    }


   
}