package com.emergencia.prontosocorro.DTO.Request;

import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.Domain.enums.SpecialistMedic;


public record FirstCareRequest(
    Long peopleId,
    Long hospitalId,
    SpecialistMedic specialistMedic,
    CareStatus careStatus,
    String cidCode,
    CareSector sector,
    SeverityLevel severityLevel
) {
    public FirstCareRequest() {
        this(null, null, null, null, null, null, null);
    }

    public FirstCareRequest toPeopleRequest() {
        return new FirstCareRequest(
            this.peopleId,
            this.hospitalId,
            this.specialistMedic,
            this.careStatus,
            this.cidCode,
            this.sector,
            this.severityLevel
        );
    }
   
}