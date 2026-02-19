package com.emergencia.prontosocorro.Controller.DTO.Request;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;

public record PeopleRequest(
    String name,
    int idade,
    String description,
    Long hospitalId,
    SeverityLevel severityLevel


) {

    public PeopleRequest() {
        this(null, 0, null, null,  null);
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



}
