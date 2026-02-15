package com.emergencia.prontosocorro.Domain.Entity.CID10;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CIDGroup {

    @Id
    private String code;

    private String description;

    @ManyToOne   // 😈 ESSENCIAL
    private CIDCategory category;

    protected CIDGroup() {}

    public CIDGroup(String code, String description) {
        this.code = code;
        this.description = description;
    }
}