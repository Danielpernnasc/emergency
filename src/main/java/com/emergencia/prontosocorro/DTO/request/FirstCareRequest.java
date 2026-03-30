package com.emergencia.prontosocorro.DTO.request;

import com.emergencia.prontosocorro.domain.enums.CareSector;
import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;


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