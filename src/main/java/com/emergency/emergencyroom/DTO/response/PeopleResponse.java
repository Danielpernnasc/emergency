package com.emergency.emergencyroom.DTO.response;

import java.util.List;

import com.emergency.emergencyroom.domain.enums.ComorbidityType;
import com.emergency.emergencyroom.domain.enums.StatusType;

public record PeopleResponse(
    Long id,
    String name,
    int idade,
    String description,
    String statePatient,
    Long hospitalId,
    String hospitalName,
    List<ComorbidityType> comorbidities
) {

    public PeopleResponse(Long id, String name, int age, String description, StatusType statusPatient, Long hospitalId,
            String hospitalName, List<ComorbidityType> comorbidities) {
        this(id, name, age, description, statusPatient.name(), hospitalId, hospitalName, comorbidities);
    }
    
}
