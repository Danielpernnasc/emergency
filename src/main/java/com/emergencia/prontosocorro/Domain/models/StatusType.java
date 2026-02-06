package com.emergencia.prontosocorro.Domain.models;

public enum StatusType {

    ENFERMO("enfermo"),
    INTERNADO("internado"),
    MORTO("morto"),
    VIVO("vivo");

    private final String state;

    StatusType(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
