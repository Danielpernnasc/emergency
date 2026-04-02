package com.emergency.emergencyroom.domain.state.status;

import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.StatePatient;

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
