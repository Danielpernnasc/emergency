package com.emergencia.prontosocorro.Domain.Entity.CID0;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.emergencia.prontosocorro.Domain.Entity.CID0.CIDCategory0;


@Entity
public class CIDGroup0 {

    
    @Id
    private String code;

    private String description;

    @ManyToOne   // 😈 ESSENCIAL
    private CIDCategory0 category0;

    protected CIDGroup0(){

    }


    public CIDGroup0(String code, String description){

        this.code = code;
        this.description = description;

    }

}
