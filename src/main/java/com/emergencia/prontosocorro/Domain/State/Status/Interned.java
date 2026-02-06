package com.emergencia.prontosocorro.Domain.State.Status;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.models.StatusType;

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
