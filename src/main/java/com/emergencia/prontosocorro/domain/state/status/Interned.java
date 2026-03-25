package com.emergencia.prontosocorro.domain.state.status;

import com.emergencia.prontosocorro.domain.state.StatePatient;
import com.emergencia.prontosocorro.domain.enums.StatusType;

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
