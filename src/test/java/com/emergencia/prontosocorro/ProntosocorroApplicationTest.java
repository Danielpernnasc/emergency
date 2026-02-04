package com.emergencia.prontosocorro;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;
import com.emergencia.prontosocorro.Service.CareService;

public class ProntosocorroApplicationTest {

    private final CareService careService = new CareService();

    @Test
    void shouldCreateFirstCareWhenPatientCanReceiveFirstCare() {
        
        People[] patients = {
            new People("João Silva", 30, "Dor no peito", new Sick(), new Hospital(1L, "Hospital Central", "Rua Principal", 100), 	new ArrayList<>())
        };

        try {
          	// FirstCare assistance = careService.createFirstCare(patients[0], patients[0].getHospital());
            //  assertNotNull(assistance);
            //  assertEquals( "Paciente:" + assistance.getPeople().getName(), );
            //  assertEquals("Hospital Central", assistance.getHospital().getNameHospital());

        for (People p : patients) {
					FirstCare assistance = careService.createFirstCare(p, p.getHospital());
                    assertNotNull(assistance);
                    assertEquals("Paciente: " + assistance.getPeople().getName()    , "Paciente: " + p.getName());
                    assertEquals("Hospital Central", assistance.getPeople().getHospital().getNameHospital());
                }

        } catch (Exception e) {
            assert false : "Exception should not be thrown for valid first care creation.";
        }
    }
       

}
