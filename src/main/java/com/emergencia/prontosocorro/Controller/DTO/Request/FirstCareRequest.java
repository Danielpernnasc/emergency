package com.emergencia.prontosocorro.Controller.DTO.Request;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;


public record FirstCareRequest(
    Long peopleId,
    Long hospitalId,
    SpecialistMedic specialistMedic
) {
    public FirstCareRequest() {
        this(null, null, null);
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


   
}