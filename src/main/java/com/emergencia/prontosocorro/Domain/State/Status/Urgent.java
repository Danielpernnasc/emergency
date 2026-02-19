package com.emergencia.prontosocorro.Domain.State.Status;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.models.StatusType;

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
