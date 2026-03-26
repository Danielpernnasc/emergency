package com.emergencia.prontosocorro;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan({
    "com.emergencia.prontosocorro.domain.entity",
    "com.emergencia.prontosocorro.infra.event"
})
@EnableJpaRepositories("com.emergencia.prontosocorro")
public class ProntosocorroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProntosocorroApplication.class, args);
    }

}
