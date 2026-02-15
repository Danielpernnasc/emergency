package com.emergencia.prontosocorro.Domain.Entity;

import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class CIDclassificationRule {
    @Id
    @GeneratedValue
    private Long id;

    private String pattern;

    // @Enumerated(EnumType.STRING)
    // private severityLevel severety;

    @Enumerated(EnumType.STRING)
    private SpecialistMedic specialistMedic;

}
