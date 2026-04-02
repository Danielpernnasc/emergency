package com.emergency.emergencyRoom.DTO.request;

import java.time.LocalDateTime;

import com.emergency.emergencyRoom.domain.enums.SeverityLevel;
import com.emergency.emergencyRoom.domain.enums.StatusType;

public record StatePatientRequest(StatusType statusType, String justification, LocalDateTime date) {
    public StatePatientRequest() {
        this(null, null, null);
    }

    public SeverityLevel severityLevel() {
    if (statusType == null) return null;

    return switch (statusType) {
        case ENFERMO -> SeverityLevel.LEVE;
        case URGENTE -> SeverityLevel.MODERADO;
        case CRITICO -> SeverityLevel.GRAVE;
        case INTERNADO -> SeverityLevel.UTI;
        case FORA_PERIGO -> SeverityLevel.OBSERVACAO;
        case MORTO -> SeverityLevel.OUTROS;
    };
}
}


