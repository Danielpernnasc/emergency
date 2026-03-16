package com.emergencia.prontosocorro.Domain.State.Status;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.enums.StatusType;

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
