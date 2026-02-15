package com.emergencia.prontosocorro.Domain.Entity.CID0;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CIDCategory0 {
    @Id
    private String code;

    private String description;

    protected CIDCategory0() {}

    public CIDCategory0(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
