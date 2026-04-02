package com.emergency.emergencyroom.domain.state.status;

import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.StatePatient;

public class StatePeopleFactory {
    public static StatePatient from(StatusType statusType){

        if(statusType == null) return null;

        String lowerState = statusType.getState().toLowerCase();

        return switch (lowerState) {
            case "enfermo" -> new Sick();
            case "urgente" -> new Urgent();
            case "critico" -> new Critical();
            case "morto" -> new Dead();
            case "internado" -> new Interned();
            default -> null;
        };
    }

}
