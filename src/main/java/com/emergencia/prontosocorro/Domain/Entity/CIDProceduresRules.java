package com.emergencia.prontosocorro.Domain.Entity;


import java.util.Set;

import com.emergencia.prontosocorro.Domain.Entity.CID10.CIDGroup;
import com.emergencia.prontosocorro.Domain.models.CareofPacients;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CIDProceduresRules {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private CIDGroup group;

    @ElementCollection
    private Set<CareofPacients> procedures;

    protected CIDProceduresRules() {}

    public CIDProceduresRules(CIDGroup group, Set<CareofPacients> procedures) {
        this.group = group;
        this.procedures = procedures;
    }

    

}
