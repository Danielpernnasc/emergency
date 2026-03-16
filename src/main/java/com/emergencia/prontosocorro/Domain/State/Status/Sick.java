package com.emergencia.prontosocorro.Domain.State.Status;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.enums.StatusType;

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
