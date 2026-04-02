package com.emergency.emergencyroom.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.DTO.request.StateEvolutionRequest;
import com.emergency.emergencyroom.domain.enums.CareStatus;
import com.emergency.emergencyroom.domain.enums.CareofPacients;

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
