package com.emergencia.prontosocorro.Service;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;



@Service
public class RegretsMedicService {

    public SpecialistMedic defineSepSpecialistMedic(People people) {
       String description = people.getDescription().toLowerCase();
       
    if(description.isEmpty() || description == null){
         return SpecialistMedic.CLINICAL_MEDICINE;
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
