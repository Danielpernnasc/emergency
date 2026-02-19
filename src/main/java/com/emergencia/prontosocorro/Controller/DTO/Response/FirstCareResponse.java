package com.emergencia.prontosocorro.Controller.DTO.Response;

import java.time.LocalDateTime;
import java.util.List;

import com.emergencia.prontosocorro.Domain.Entity.CID;
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
        String hospitalName,
        String cidCode

) {

    public FirstCareResponse(Long id, CareStatus careStatus, SpecialistMedic specialistMedic, LocalDateTime careDateTime,
            List<ComorbidityType> comorbidities, Long peopleId, String peopleName, Long hospitalId,
            String hospitalName, String cidCode) {
            this.id = id;
            this.careStatus = careStatus;
            this.specialistMedic = specialistMedic;
            this.careDateTime = careDateTime;
            this.comorbidities = comorbidities;
            this.peopleId = peopleId;
            this.peopleName = peopleName;
            this.hospitalId = hospitalId;
            this.hospitalName = hospitalName;
            this.cidCode = cidCode;
    }

}

    