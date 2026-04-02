package com.emergency.emergencyRoom.domain.state.status;

import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.StatePatient;

public class Urgent implements StatePatient {
    @Override
    public boolean canReceiveCare() {
        return true;
    }

    @Override
    public String toString() {
        return "Urgente";
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.URGENTE;
    }

}
