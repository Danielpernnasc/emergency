package com.emergencia.prontosocorro.Domain.State.Status;

import com.emergencia.prontosocorro.Domain.State.StatePatient;
import com.emergencia.prontosocorro.Domain.models.StatusType;

public class Live implements  StatePatient {

    @Override
    public boolean canReceiveCare() {
        return false;
    }

     @Override
    public String toString() {
        return "Vivo";
    }

  
    @Override
    public StatusType getStatusType() {
        return StatusType.VIVO;
    }

}
