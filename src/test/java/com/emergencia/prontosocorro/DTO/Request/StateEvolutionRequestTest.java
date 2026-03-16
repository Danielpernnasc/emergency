package com.emergencia.prontosocorro.DTO.Request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.CareofPacients;

import org.junit.jupiter.api.Test;

public class StateEvolutionRequestTest {

    @Test
    void shouldStateEvolutionRequest(){
        StateEvolutionRequest req = new StateEvolutionRequest(
             Set.of(CareofPacients.MEDICACAO),
            CareStatus.EM_ATENDIMENTO

        );

        assertEquals(Set.of(CareofPacients.MEDICACAO), req.procedure());
        assertEquals(CareStatus.EM_ATENDIMENTO, req.careStatus());
    }

}
