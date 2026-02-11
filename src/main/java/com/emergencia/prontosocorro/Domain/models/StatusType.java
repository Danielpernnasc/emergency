package com.emergencia.prontosocorro.Domain.models;

public enum StatusType {

    ENFERMO("enfermo"),
    INTERNADO("internado"),
    MORTO("morto");

    private final String state;

    StatusType(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
