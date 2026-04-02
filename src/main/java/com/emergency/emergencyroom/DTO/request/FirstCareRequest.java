package com.emergency.emergencyroom.DTO.request;

import com.emergency.emergencyroom.domain.enums.CareSector;
import com.emergency.emergencyroom.domain.enums.CareStatus;
import com.emergency.emergencyroom.domain.enums.SeverityLevel;
import com.emergency.emergencyroom.domain.enums.SpecialistMedic;


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