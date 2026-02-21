package com.emergencia.prontosocorro.Domain.Entity;

import java.util.Set;

import com.emergencia.prontosocorro.Domain.models.SeverityLevel;
import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class CID {
    @Id
    private String code;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severityLevel;

    @Enumerated(EnumType.STRING)
    private SpecialistMedic specialistMedic;

    protected CID() {
    // obrigatório para JPA
    }

     public CID(String code, String description, SeverityLevel severityLevel, SpecialistMedic specialistMedic) {
        this.code = code;
        this.description = description;
        this.severityLevel = severityLevel;
        this.specialistMedic = specialistMedic;
    }


     public String getCode() {
        return code;
     }

     public void setCode(String trim) {
        this.code = trim;
     }

     public void setDescription(String trim) {
        this.description = trim;
     }


     public SeverityLevel getSeverityLevel() {
         return severityLevel;
      }

      public String getDescription() {
         return description;
      }

      public Set<SpecialistMedic> getSpecialistMedic() {
            return Set.of(specialistMedic);
      }

}
