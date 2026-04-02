package com.emergency.emergencyroom.domain.entity;

import com.emergency.emergencyroom.domain.enums.SpecialistMedic;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "cid_keyword_rule")
public class CIDKeywordRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    @ManyToOne
    @JoinColumn(name = "cid_code")
    private CID cid;

    @Enumerated(EnumType.STRING)
    private SpecialistMedic specialistMedic;


    public CIDKeywordRule(){}

    public CIDKeywordRule(String keyword, CID cid, SpecialistMedic specialistMedic) {
        this.keyword = keyword;
        this.cid = cid;
        this.specialistMedic = specialistMedic;
    }

    public Long getId() {
        return id;
    }

     public String getKeyword() {
        return keyword;
    }

    public CID getCid() {
        return cid;
    }

    public void setKeyword(String word) {
        this.keyword = word;
    }

    public void setCidCode(String code) {
        this.cid = new CID(code, null, null, null);
    }

    public SpecialistMedic getSpecialistMedic() {
        return specialistMedic;
    }

    public void setSpecialistMedic(SpecialistMedic specialistMedic) {
        this.specialistMedic = specialistMedic;
    }



}
