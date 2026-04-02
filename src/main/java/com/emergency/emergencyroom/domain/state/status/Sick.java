package com.emergency.emergencyroom.domain.state.status;

import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.StatePatient;

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
