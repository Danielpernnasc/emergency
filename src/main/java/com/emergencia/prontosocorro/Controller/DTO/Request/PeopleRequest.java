package com.emergencia.prontosocorro.Controller.DTO.Request;

public record PeopleRequest(
    String name,
    int idade,
    String description,
    String statepatient,
    Long hospitalId

) {

    public PeopleRequest() {
        this(null, 0, null, null, null);
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

}
