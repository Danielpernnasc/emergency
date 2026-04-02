package com.emergency.emergencyroom.infra.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.emergency.emergencyroom.infra.config.RabbitMQConfig;

 class RabbitMQConfigTest {
 private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void shouldCreateConnectionFactory() {
          ConnectionFactory factory = new CachingConnectionFactory(); // fake
            RabbitTemplate template = config.rabbitTemplate(factory);

            assertNotNull(template);
    }

    @Test
    void shouldCreateMainQueue() {
        Queue queue = config.getqueue();
        assertEquals(RabbitMQConfig.QUEUE, queue.getName());
    }

    @Test
    void shouldCreateDeadLetterQueue() {
        Queue queue = config.deadLetterQueue();
        assertEquals(RabbitMQConfig.DLQ, queue.getName());
    }

    @Test
    void shouldCreateSectorQueue() {
        Queue queue = config.sectorQueue();
        assertEquals(RabbitMQConfig.SECTOR_QUEUE, queue.getName());
    }

    @Test
    void shouldCreateExchange() {
        DirectExchange exchange = config.exchange();
        assertEquals(RabbitMQConfig.EXCHANGE, exchange.getName());
    }

    @Test
    void shouldCreateDeadLetterExchange() {
        DirectExchange exchange = config.deadLetterExchange();
        assertEquals(RabbitMQConfig.DLX, exchange.getName());
    }

    @Test
    void shouldCreateBindings() {
        assertNotNull(config.binding());
        assertNotNull(config.dlqBinding());
        assertNotNull(config.sectorBinding());
    }

    @Test
    void shouldCreateMessageConverter() {
        assertNotNull(config.messageConverter());
    }

    @Test
    void shouldCreateRabbitTemplate() {
     ConnectionFactory factory = new CachingConnectionFactory();
        RabbitTemplate template = config.rabbitTemplate(factory);
        assertNotNull(template);
    }

    @Test
    void shouldCreateListenerFactory() {
      ConnectionFactory factory = new CachingConnectionFactory();
        assertNotNull(config.rabbitListenerContainerFactory(factory));
    }

    @Test
    void shouldCreateAmqpAdmin() {
      ConnectionFactory factory = new CachingConnectionFactory();
        assertNotNull(config.amqpAdmin(factory));
    }
}
