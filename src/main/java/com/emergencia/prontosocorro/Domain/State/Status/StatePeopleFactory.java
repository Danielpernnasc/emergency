package com.emergencia.prontosocorro.Domain.State.Status;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public class StatePeopleFactory {
    public static StatePatient from(StatusType statusType){

        if(statusType == null) return null;

        String lowerState = statusType.getState().toLowerCase();

        return switch (lowerState) {
            case "enfermo" -> new Sick();
            case "morto" -> new Dead();
            case "vivo" -> new Live();
            default -> null;
        };
    }

}
