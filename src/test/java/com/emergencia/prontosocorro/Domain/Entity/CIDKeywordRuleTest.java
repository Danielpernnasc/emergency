package com.emergencia.prontosocorro.Domain.Entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CIDKeywordRuleTest {

    @Test
    void ShoudConstructorCIDKeywordRule(){

        CIDKeywordRule cidKeywordRule = new CIDKeywordRule(
            "dor no peito",
            new CID(
                "R07",
                "Dor no peito",
                com.emergencia.prontosocorro.Domain.enums.SeverityLevel.MODERADO,
                com.emergencia.prontosocorro.Domain.enums.SpecialistMedic.CARDIOLOGIST
            ),
            com.emergencia.prontosocorro.Domain.enums.SpecialistMedic.CARDIOLOGIST
        );

      
   
        assertEquals("dor no peito", cidKeywordRule.getKeyword());
        assertEquals("R07", cidKeywordRule.getCid().getCode());
        assertEquals("Dor no peito", cidKeywordRule.getCid().getDescription());
        assertEquals(com.emergencia.prontosocorro.Domain.enums.SeverityLevel.MODERADO, cidKeywordRule.getCid().getSeverityLevel());
        assertEquals(com.emergencia.prontosocorro.Domain.enums.SpecialistMedic.CARDIOLOGIST, cidKeywordRule.getCid().getSpecialistMedic());
    }


}
