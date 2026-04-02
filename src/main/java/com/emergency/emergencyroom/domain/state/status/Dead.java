package com.emergency.emergencyroom.domain.state.status;
import com.emergency.emergencyroom.domain.enums.StatusType;
import com.emergency.emergencyroom.domain.state.StatePatient;


public class Dead implements StatePatient {

    @Override
    public boolean canReceiveCare(){
        return false;
    }

    @Override
    public String toString() {
        return "Morto";
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.MORTO;
    }

}
