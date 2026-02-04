package com.emergencia.prontosocorro.Service;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public class RegretMedicServiceTest {

    private final RegretsMedicService regretsMedic = new RegretsMedicService(
        new People("Maria Souza", 25, "Fratura no braço", new Sick(), new Hospital(2L, "Hospital São Lucas", "Avenida Secundária", 200), 	new ArrayList<>()),
        new Hospital(2L, "Hospital São Lucas", "Avenida Secundária", 200)
    );

    @Test
    void deveRetornarOrtopedistaQuandoDescricaoContemFraturaOuOssos() {
        People people = new People("Maria Souza", 25, "Fratura no braço", new Sick(), new Hospital(2L, "Hospital São Lucas", "Avenida Secundária", 200), new ArrayList<>());
        people.setDescription("Paciente com fratura no braço");

        SpecialistMedic specialistMedic = regretsMedic.defineSepSpecialistMedic(people);

        assert(specialistMedic == SpecialistMedic.ORTHOPEDIST);
    }

    @Test
    void shouldRegisterDeathAfterHospitalCare(){
        CareService careService = new CareService();
        Hospital hospital = new Hospital(3L, "Hospital Sul", "Travessa Terciária", 30);
        People people = new People(
            "Ciclando Sobrenome",
            60,
            "Dor no peitos",
            new Sick(),
            hospital,
            new ArrayList<>()
        );
        people.registerDeath("Parada cardiorrespiratória");
        assertEquals(StatusType.MORTO, people.getStatePatient().getStatusType());
    }
}
