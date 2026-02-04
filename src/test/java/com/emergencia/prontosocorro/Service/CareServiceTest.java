package com.emergencia.prontosocorro.Service;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;

public class CareServiceTest {

    @Test
    FirstCare testCreateFirstCare(People people, Hospital hospital, SpecialistMedic specialistMedic) {
        if(people == null || hospital == null || specialistMedic == null) {
            throw new IllegalArgumentException("People, Hospital, and SpecialistMedic must not be null");
        }

        if(people.getStatePatient() == null){
            throw new IllegalArgumentException("StatePatient must not be null");
        }

        if(!people.getStatePatient().canReceiveCare()) {
            throw new IllegalStateException("Patient is not in a state to receive care");
        }

         return new FirstCare(hospital, people, specialistMedic);
    }

    @Test
    void shouldCreateFirstCareWhenPatientCanReceiveFirstCare() {
       People patient = new People(
        "Maria Souza", 
        45, 
        "Fratura na perna", 
        new Sick(),
        new Hospital(2L, "Hospital São Lucas", "Avenida Secundária", 200),
        new ArrayList<>()
    
    );
        CareService service = new CareService();

        FirstCare firstCare = service.createFirstCare(patient, patient.getHospital());
            assertNotNull(firstCare);
            assertNotNull(firstCare.getSpecialistMedic());
    }    

}
