package com.emergencia.prontosocorro.DTO.Request;

import java.util.Set;

import com.emergencia.prontosocorro.domain.enums.CareStatus;
import com.emergencia.prontosocorro.domain.enums.CareofPacients;

public record StateEvolutionRequest(
                Set<CareofPacients> procedure,
                CareStatus careStatus
            ) {}
