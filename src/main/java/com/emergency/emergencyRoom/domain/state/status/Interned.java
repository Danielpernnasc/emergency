package com.emergency.emergencyRoom.domain.state.status;

import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.StatePatient;

public class Interned implements StatePatient {
    @Override
    public boolean canReceiveCare() {
        return true;
    }

    @Override
    public String toString() {
        return "Internado";
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.INTERNADO;
    }

}
