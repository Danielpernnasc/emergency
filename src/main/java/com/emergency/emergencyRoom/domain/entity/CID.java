package com.emergency.emergencyRoom.domain.entity;

import com.emergency.emergencyRoom.domain.enums.SeverityLevel;
import com.emergency.emergencyRoom.domain.enums.SpecialistMedic;

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



    public CID() {
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

  public void setCode(String code) {
    this.code = code;
}

   public void setDescription(String description) {
      this.description = description;
   }


     public SeverityLevel getSeverityLevel() {
         return severityLevel;
      }

      public String getDescription() {
         return description;
      }

  public SpecialistMedic getSpecialistMedic() {
    return specialistMedic;
   }

}
