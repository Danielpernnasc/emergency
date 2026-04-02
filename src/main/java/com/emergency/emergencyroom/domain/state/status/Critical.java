package com.emergency.emergencyroom.domain.state.status;

import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.StatePatient;

public class Critical implements StatePatient {
    @Override
    public boolean canReceiveCare() {
        return true;
    }

    @Override
    public String toString() {
        return "Crítico";
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.CRITICO;
    }

}
