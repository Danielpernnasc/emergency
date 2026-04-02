package com.emergency.emergencyRoom.DTO.request;

import java.util.Set;

import com.emergency.emergencyRoom.domain.enums.CareStatus;
import com.emergency.emergencyRoom.domain.enums.CareofPacients;

public record StateEvolutionRequest(
                Set<CareofPacients> procedure,
                CareStatus careStatus
            ) {}
