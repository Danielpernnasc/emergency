package com.emergencia.prontosocorro.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.DTO.request.StatePatientRequest;
import com.emergencia.prontosocorro.domain.enums.SeverityLevel;
import com.emergencia.prontosocorro.domain.enums.StatusType;

public class StatePatientRequestTest {

    @Test
    void shouldStatePatientRequest(){

        LocalDateTime date = LocalDateTime.now();
         
        StatePatientRequest req = new StatePatientRequest(
            StatusType.CRITICO,
            "Doença Contagiosa",
            date
        );

        assertEquals(StatusType.CRITICO, req.statusType());
        assertEquals("Doença Contagiosa", req.justification());
        assertEquals(date, req.date());


    }

    @Test
    void shouldStatePatientRequestNull(){
        StatePatientRequest req = new StatePatientRequest();

        assertNull(req.statusType());
        assertNull(req.justification());
        assertNull(req.date());
    }

   @Test
    void shouldReturnLeveWhenStatusEnfermo() {

        StatePatientRequest renull = new StatePatientRequest();

        StatePatientRequest reqenf = new StatePatientRequest(
            StatusType.ENFERMO, null, null
        );
          StatePatientRequest requrg =  new StatePatientRequest(
            StatusType.URGENTE, null, null
        );
          StatePatientRequest reqcrit =  new StatePatientRequest(
            StatusType.CRITICO, null, null
        );
          StatePatientRequest reqint =  new StatePatientRequest(
            StatusType.INTERNADO, null, null
        );
          StatePatientRequest reqFP =  new StatePatientRequest(
            StatusType.FORA_PERIGO, null, null
        );
        StatePatientRequest reqMort = new StatePatientRequest(
            StatusType.MORTO, null, null
        );

        assertEquals(null, renull.severityLevel());
        assertEquals(SeverityLevel.LEVE, reqenf.severityLevel());
        assertEquals(SeverityLevel.MODERADO, requrg.severityLevel());
        assertEquals(SeverityLevel.GRAVE, reqcrit.severityLevel());
        assertEquals(SeverityLevel.UTI, reqint.severityLevel());
        assertEquals(SeverityLevel.OBSERVACAO, reqFP.severityLevel());
        assertEquals(SeverityLevel.OUTROS, reqMort.severityLevel());
      
    }





}
