package com.emergencia.prontosocorro.DTO.Request;

import java.util.Set;

import com.emergencia.prontosocorro.Domain.enums.CareStatus;
import com.emergencia.prontosocorro.Domain.enums.CareofPacients;


public record StateEvolutionRequest(
                Set<CareofPacients> procedure,
                CareStatus careStatus
            ) {}
