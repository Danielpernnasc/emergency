package com.emergency.emergencyRoom.domain.enums;

public enum StatusType {

    ENFERMO("enfermo"),
    INTERNADO("internado"),
    URGENTE("urgente"),
    CRITICO("critico"),
    FORA_PERIGO("fora de perigo"),
    MORTO("morto");

    private final String state;

    StatusType(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
