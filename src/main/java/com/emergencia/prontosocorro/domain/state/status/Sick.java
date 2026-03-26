package com.emergencia.prontosocorro.domain.state.status;

import com.emergencia.prontosocorro.domain.state.StatePatient;
import com.emergencia.prontosocorro.domain.enums.StatusType;

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
