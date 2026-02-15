package com.emergencia.prontosocorro.Domain.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Capitulos {

        @Id
        private String code;  
    
        private String description;
    
        protected Capitulos() {}
    
        public Capitulos(String code, String description) {
            this.code = code;
            this.description = description;
        }

}
