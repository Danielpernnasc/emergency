package com.emergencia.prontosocorro.Controller.DTO.Request;

import java.util.Set;

import com.emergencia.prontosocorro.Domain.models.CareofPacients;

public record StateEvolutionRequest(
                Set<CareofPacients> procedure) {
        public StateEvolutionRequest() {
                this(null);
        }

        public StateEvolutionRequest(Set<CareofPacients> procedure) {
                this.procedure = procedure;
        }
}
