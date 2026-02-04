package com.emergencia.prontosocorro.Service;

import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.SpecialistMedic;

public class RegretsMedicService {

    private People people;
    private Hospital hospital;



    public RegretsMedicService(People people, Hospital hospital) {
        this.people = people;
        this.hospital = hospital;

   
    }

     public People getPeople() {
        return people;
     }

    public Hospital getHospital() {
        return hospital;
    }

    public SpecialistMedic defineSepSpecialistMedic(People people) {
       String description = people.getDescription().toLowerCase();
       
       if(description == null || description.isEmpty()) {
         return SpecialistMedic.CLINICAL_MEDICINE;
       }  

       
    if(description.contains("fratura") || description.contains("ossos")){
         return SpecialistMedic.ORTHOPEDIST;
    }
   
    if(description.contains("queimadura")){
        return SpecialistMedic.DERMATOLOGIST;
    }

    if(description.contains("peito") 
        || description.contains("card")
        || description.contains("infarto")
        || description.contains("coracao")
    ){
        return SpecialistMedic.CARDIOLOGIST;
    }

    if(description.contains("Trauma perfurante") 
        || 
    description.contains("apendicite")
     ){
        return SpecialistMedic.CIRURGIA_GENERALIST;
    }

    if(description.contains("óbito")){
        throw new IllegalArgumentException("O paciente não pode receber atendimento médico.");
    }
     
    // Default specialist if no condition matches
    return SpecialistMedic.CLINICAL_MEDICINE;
    }

}
