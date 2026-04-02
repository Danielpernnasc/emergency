package com.emergency.emergencyroom.domain.enums;

public enum ComorbidityType {
    HIPERTENSAO,
    DIABETES,
    DOENCA_DE_CHAGAS,
    ASMA,
    DOENCA_RENAL_CRONICA,
    CARDIOPATIA,
    OBESIDADE,
    ALERGIA,
    OUTRA;



   public SeverityLevel getSeverityLevel()  {
        return switch (this) {
            case HIPERTENSAO, DIABETES, CARDIOPATIA -> SeverityLevel.GRAVE;
            case DOENCA_DE_CHAGAS, ASMA, DOENCA_RENAL_CRONICA -> SeverityLevel.MODERADO;
            case OBESIDADE, ALERGIA -> SeverityLevel.LEVE;
            case OUTRA -> SeverityLevel.LEVE;
        };
    }
}
