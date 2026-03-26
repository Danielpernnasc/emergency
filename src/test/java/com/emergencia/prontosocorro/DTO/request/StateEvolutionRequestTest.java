package com.emergencia.prontosocorro.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.emergencia.prontosocorro.DTO.request.StateEvolutionRequest;
import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.CareofPacients;

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
