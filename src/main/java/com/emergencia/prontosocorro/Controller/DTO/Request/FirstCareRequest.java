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
    Long cidId
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
        if(this.specialistMedic == null) {
            return CareStatus.AGUARDANDO_ATENDIMENTO;
        }
        return CareStatus.EM_ATENDIMENTO; // Valor fixo para o status inicial
    }

    public List<ComorbidityType> getComorbidities() {
        return comorbidities;
    }

    public Long getCidId() {
        return cidId;
    }
   
}