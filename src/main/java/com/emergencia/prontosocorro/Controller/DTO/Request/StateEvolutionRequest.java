package com.emergencia.prontosocorro.Controller.DTO.Request;

import java.util.Set;

import com.emergencia.prontosocorro.Domain.models.CareStatus;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;

public record StateEvolutionRequest(
                Set<CareofPacients> procedure,
                CareStatus careStatus) {
}
