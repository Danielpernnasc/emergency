package com.emergency.emergencyRoom.DTO.request;
import java.util.List;

import com.emergency.emergencyRoom.domain.enums.ComorbidityType;
import com.emergency.emergencyRoom.domain.enums.SeverityLevel;

public record PeopleRequest(
    String name,
    int idade,
    String description,
    Long hospitalId,
    SeverityLevel severityLevel,
    List<ComorbidityType> comorbidities


) {

    public PeopleRequest() {
        this(null, 0, null, null, null, null);
    }
       
    public String getName(){
        return name;
    }

    public int getIdade(){
        return idade;
    }

    public String getDescription(){
        return description;
    }

    public Long getHospitalId(){
        return hospitalId;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public List<ComorbidityType> comorbidities() {
        return comorbidities;
    }
}
