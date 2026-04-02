package com.emergency.emergencyRoom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan({
    "com.emergency.emergencyRoom.domain.entity",
    "com.emergency.emergencyRoom.infra.event"
})
@EnableJpaRepositories("com.emergency.emergencyRoom")
public class ProntosocorroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProntosocorroApplication.class, args);
    }

}
