package com.emergency.emergencyRoom.domain.state.status;
import com.emergency.emergencyRoom.domain.enums.StatusType;
import com.emergency.emergencyRoom.domain.state.StatePatient;


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
