package com.emergencia.prontosocorro.Controller.DTO.Request;

import java.util.List;

import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;


public record FirstCareRequest(
    Long peopleId,
    Long hospitalId,
    SpecialistMedic specialistMedic,
    CareStatus careStatus,
    String cidCode
) {
    public FirstCareRequest() {
        this(null, null, null, null, null);
    }

    public Long getPeopleId() {
        return peopleId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public SpecialistMedic getSpecialistMedic() {
        return specialistMedic;
    }

 
    public CareStatus getCareStatus() {
        return careStatus;
    }


    public String getCidCode() {
        return cidCode;
    }

    public FirstCareRequest toPeopleRequest() {
        return new FirstCareRequest(
            this.peopleId,
            this.hospitalId,
            this.specialistMedic,
            this.careStatus,
            this.cidCode
        );
    }
   
}