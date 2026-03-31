package com.emergencia.prontosocorro;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.amqp.rabbit.core.RabbitTemplate;



@SpringBootTest(
    classes = ProntosocorroApplication.class,
    properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
    }
)

@ActiveProfiles("test")
class ProntosocorroApplicationTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void shouldLoadContext() {
         assertTrue(true);
    }

  }