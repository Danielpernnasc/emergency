package com.emergencia.prontosocorro.domain.enums;


import java.util.Set;

public enum CareofPacients {
     MEDICACAO,
     CURATIVO,
     SUTURA,
     IMOBILIZACAO,
     OBSERVACAO,
     OXIGENIOTERAPIA,
     CINTILOGRAFIA,
     EXAMES_IMAGEM,
     CIRURGIA;

     private Set<CareofPacients> procedures;

     public Set<CareofPacients> getProcedures() {
          return procedures;
     }

     public void setProcedures(Set<CareofPacients> procedures) {
          this.procedures = procedures;
     }
}
