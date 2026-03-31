package com.emergencia.prontosocorro.infra.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

 class RabbitMQConfigTest {
 private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void shouldCreateConnectionFactory() {
        ConnectionFactory factory = config.connectionFactory();
        assertNotNull(factory);
    }

    @Test
    void shouldCreateMainQueue() {
        Queue queue = config.queue();
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
        ConnectionFactory factory = config.connectionFactory();
        RabbitTemplate template = config.rabbitTemplate(factory);
        assertNotNull(template);
    }

    @Test
    void shouldCreateListenerFactory() {
        ConnectionFactory factory = config.connectionFactory();
        assertNotNull(config.rabbitListenerContainerFactory(factory));
    }

    @Test
    void shouldCreateAmqpAdmin() {
        ConnectionFactory factory = config.connectionFactory();
        assertNotNull(config.amqpAdmin(factory));
    }
}
