package com.emergencia.prontosocorro.Service;

import org.springframework.stereotype.Service;

import com.emergencia.prontosocorro.Domain.Entity.CID;
import com.emergencia.prontosocorro.Domain.models.SeverityLevel;

@Service
public class SeverityClassifierService {

  
    public SeverityLevel classifySeverity(CID cid) {

        if (cid == null)
            throw new IllegalArgumentException("CID obrigatório");

        return cid.getSeverityLevel();
    }

}
