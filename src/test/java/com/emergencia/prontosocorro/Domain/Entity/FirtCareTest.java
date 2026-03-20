package com.emergencia.prontosocorro.Domain.Entity;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.Domain.enums.CareSector;
import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.CareofPacients;
import com.emergencia.prontosocorro.Domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.Domain.enums.SpecialistMedic;
import com.emergencia.prontosocorro.Domain.enums.StatusType;
 

public class FirtCareTest {

    @Test
    void shouldCreateFirstCareUsingConstructor(){
        Hospital hospital = new Hospital();
        People patient = new People();
        CID cid = new CID(
            "V49",
            "Acidente de transporte",
            SeverityLevel.GRAVE,
            SpecialistMedic.CARDIOLOGIST
       
        );
       
        SpecialistMedic medics = SpecialistMedic.CARDIOLOGIST;
        CareStatus status = CareStatus.EM_ATENDIMENTO;
        CareSector sector = CareSector.TRIAGEM;

    
        FirstCare firstCare = new FirstCare(
            patient,
            cid,
            hospital,
            medics,
            status,
            sector

        );

        assertEquals(patient, firstCare.getPeople());
        assertEquals("V49", cid.getCode());
        assertEquals("Acidente de transporte", cid.getDescription());
        assertEquals(hospital, firstCare.getHospital());
        assertEquals(SpecialistMedic.CARDIOLOGIST, firstCare.getSpecialistMedic());
        assertEquals(CareStatus.EM_ATENDIMENTO, firstCare.getCareStatus());
        assertNotNull(firstCare.getCareDateTime());
        assertTrue(firstCare.getProcedures().isEmpty());
        assertEquals(sector, firstCare.getSector());
    }

    @Test
    void shouldFirstCareget(){
        FirstCare firstCare = new FirstCare();
        firstCare.setId(1L);
        assertEquals(1L, firstCare.getId());
    }

    @Test
    public void shouldDischargePatient() {
       People patient = new People();  
       patient.setStatusPatient(StatusType.ENFERMO);

       FirstCare firstCare = new FirstCare();
       firstCare.setPeople(patient);
       firstCare.disCharge();

       assertEquals(CareStatus.DE_ALTA, firstCare.getCareStatus());
    }


    @Test
    public void shouldNotDischargePatient() {
        People patient = new People();
        patient.setStatusPatient(StatusType.MORTO);
        FirstCare firstCare = new FirstCare();
        firstCare.setPeople(patient);
        
        assertThrows(IllegalStateException.class, () -> firstCare.disCharge());
    }

     @Test
    void shouldThrowExceptionWhenSeverityIsNull() {
        People people = new People();

        assertThrows(
            IllegalArgumentException.class,
            () -> people.changeStatus(null)
        );
    }

    @Test
    void shouldprePersist(){
        FirstCare firstCare = new FirstCare();
        firstCare.prePersist();

        assertNotNull(firstCare.getCareDateTime());
    }

    @Test
    void shouldSetProcedures(){
        FirstCare firstCare = new FirstCare();
    
        Set<CareofPacients> procedures = Set.of(CareofPacients.CURATIVO);

        firstCare.setProcedures(procedures);

        assertEquals(procedures, firstCare.getProcedures());
    }

   



  

}