package com.emergency.emergencyroom.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

import com.emergency.emergencyroom.domain.enums.CareSector;
import com.emergency.emergencyroom.domain.enums.CareStatus;
import com.emergency.emergencyroom.domain.enums.ComorbidityType;
import com.emergency.emergencyroom.domain.enums.SpecialistMedic;

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
        String cidCode,
        CareSector sector

) {

    public FirstCareResponse(Long id, CareStatus careStatus, SpecialistMedic specialistMedic, LocalDateTime careDateTime,
            List<ComorbidityType> comorbidities, Long peopleId, String peopleName, Long hospitalId,
            String hospitalName, String cidCode, CareSector sector) {
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
            this.sector = sector;
    }

}

    