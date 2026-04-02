package com.emergency.emergencyroom.DTO.request;

import java.util.Set;

import com.emergency.emergencyroom.domain.enums.CareStatus;
import com.emergency.emergencyroom.domain.enums.CareofPacients;

public record StateEvolutionRequest(
                Set<CareofPacients> procedure,
                CareStatus careStatus
            ) {}
