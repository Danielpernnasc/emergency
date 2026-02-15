package com.emergencia.prontosocorro.Domain.Entity.CID10;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CIDCategory {

        @Id
        private String code;

        private String description;

       protected CIDCategory() {}

       public CIDCategory(String code, String description) {
                this.code = code;
                this.description = description;
       }  


}
