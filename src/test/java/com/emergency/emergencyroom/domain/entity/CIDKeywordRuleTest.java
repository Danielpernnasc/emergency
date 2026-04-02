package com.emergency.emergencyroom.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.emergency.emergencyroom.domain.entity.CID;
import com.emergency.emergencyroom.domain.entity.CIDKeywordRule;

public class CIDKeywordRuleTest {

    @Test
    void ShoudConstructorCIDKeywordRule(){

        CIDKeywordRule cidKeywordRule = new CIDKeywordRule(
            "dor no peito",
            new CID(
                "R07",
                "Dor no peito",
                com.emergency.emergencyroom.domain.enums.SeverityLevel.MODERADO,
                com.emergency.emergencyroom.domain.enums.SpecialistMedic.CARDIOLOGIST
            ),
            com.emergency.emergencyroom.domain.enums.SpecialistMedic.CARDIOLOGIST
        );

      
   
        assertEquals("dor no peito", cidKeywordRule.getKeyword());
        assertEquals("R07", cidKeywordRule.getCid().getCode());
        assertEquals("Dor no peito", cidKeywordRule.getCid().getDescription());
        assertEquals(com.emergency.emergencyroom.domain.enums.SeverityLevel.MODERADO, cidKeywordRule.getCid().getSeverityLevel());
        assertEquals(com.emergency.emergencyroom.domain.enums.SpecialistMedic.CARDIOLOGIST, cidKeywordRule.getCid().getSpecialistMedic());
    }


}
