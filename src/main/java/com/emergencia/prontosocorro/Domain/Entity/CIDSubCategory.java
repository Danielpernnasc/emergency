package com.emergencia.prontosocorro.Domain.Entity;

import com.emergencia.prontosocorro.Domain.Entity.CID10.CIDGroup;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CIDSubCategory {

    @Id
    private String code;  

    private String description;

    @ManyToOne
    private CIDGroup group;

     protected CIDSubCategory() {}

     public CIDSubCategory(String code, String description) {
        this.code = code;
        this.description = description;
     }

}
