package com.emergencia.prontosocorro.Controller.DTO.Request;

import java.util.List;

import com.emergencia.prontosocorro.Domain.models.ComorbidityType;

public record PeopleRequest(
    String name,
    int idade,
    String description,
    String statepatient,
    Long hospitalId,
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

    public List<ComorbidityType> getComorbidities() {
        return comorbidities;
    }
}
