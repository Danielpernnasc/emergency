package com.emergencia.prontosocorro.Controller.DTO.Request;

import java.util.List;

import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;


public record FirstCareRequest(
    Long peopleId,
    Long hospitalId,
    SpecialistMedic specialistMedic,
    List<ComorbidityType> comorbidities,
    CareStatus careStatus,
    String cidCode
) {
    public FirstCareRequest() {
        this(null, null, null, null, null, null);
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
        return CareStatus.EM_ATENDIMENTO; 
    }

    public List<ComorbidityType> getComorbidities() {
        return comorbidities;
    }

    public String getCidCode() {
        return cidCode;
    }
   
}