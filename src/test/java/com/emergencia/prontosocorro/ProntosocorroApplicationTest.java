package com.emergencia.prontosocorro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
  properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration"
  }
)
@ActiveProfiles("test")
class ApplicationTests {

    @Test
    void contextLoads() {
    }
}

