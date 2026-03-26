package com.emergencia.prontosocorro.Domain.Entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.domain.entity.CID;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.domain.enums.SpecialistMedic;

public class CIDTest {

    @Test   
    void shouldConstructorCID(){
    
            CID cid = new CID(
                "A00",
                "Paciente apresenta Coléra, sinas de vomitos e manchas na pele",
                SeverityLevel.MODERADO,
                SpecialistMedic.CLINICAL_MEDICINE
            );
       

        assertEquals("A00", cid.getCode());
        assertEquals("Paciente apresenta Coléra, sinas de vomitos e manchas na pele", cid.getDescription());  
        assertEquals(SeverityLevel.MODERADO, cid.getSeverityLevel());
        assertEquals(SpecialistMedic.CLINICAL_MEDICINE, cid.getSpecialistMedic()); 

    }

    


}
