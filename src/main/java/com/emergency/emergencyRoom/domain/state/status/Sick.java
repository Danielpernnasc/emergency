package com.emergency.emergencyRoom.domain.state.status;

import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.StatePatient;

public class Sick implements StatePatient {

    @Override
    public boolean canReceiveCare() {
        return true;
    }

    @Override
    public String toString() {
        return "Enfermo";
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.ENFERMO;
    }

}
