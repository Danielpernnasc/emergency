package com.emergency.emergencyroom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan({
    "com.emergency.emergencyroom.domain.entity",
    "com.emergency.emergencyroom.infra.event"
})
@EnableJpaRepositories("com.emergency.emergencyroom")
public class ProntosocorroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProntosocorroApplication.class, args);
    }

}
