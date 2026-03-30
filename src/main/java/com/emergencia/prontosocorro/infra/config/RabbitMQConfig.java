package com.emergencia.prontosocorro.infra.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.support.converter.MessageConverter;

@Profile("!prod")
@Configuration
public class RabbitMQConfig {


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory =
            new CachingConnectionFactory("jackal.rmq.cloudamqp.com");
        factory.setUsername("kabgouoe");
        factory.setPassword("SPnOtKbACZlSZBxarxzR1HtsT9S3xOkA");
        factory.setVirtualHost("kabgouoe");


        return factory;
    }

    //Constantes (mantém tudo)

    public static final String EXCHANGE = "hospital.exchange";
    public static final String QUEUE = "hospital.transfer.queue";
    public static final String ROUTING_KEY = "patient.transfer";

    public static final String DLQ = "hospital.transfer.dlq";
    public static final String DLX = "hospital.dlx";
    public static final String DLQ_ROUTING_KEY = "patient.transfer.dlq";

    public static final String SECTOR_QUEUE = "hospital.sector.queue";
    public static final String SECTOR_ROUTING_KEY = "patient.sector.changed";
    
  //🔥 2. FILA PRINCIPAL (com DLQ)
    @Bean
    public Queue queue(){
        return  QueueBuilder.durable(QUEUE)
                    .withArgument("x-dead-letter-exchange", DLX)
                    .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                    .build();
    }

    //🔥 3. DLQ

    @Bean
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Queue sectorQueue(){
        return QueueBuilder.durable(SECTOR_QUEUE).build();
    }

    //🔥 4. Exchanges

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DLX);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding(){
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding sectorBinding(){
        return BindingBuilder
            .bind(sectorQueue())
            .to(exchange())
            .with(SECTOR_ROUTING_KEY);
    }

    // 🔥CONVERTER JSON (ESSENCIAL)

   @Bean
    public MessageConverter messageConverter() {
            ObjectMapper objectMapper = new ObjectMapper();
            return new Jackson2JsonMessageConverter(objectMapper);
    }

        // 🔥 TEMPLATE COM CONVERTER
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }


        // 🔥 CONSUMER TAMBÉM USA JSON
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
        ConnectionFactory connectionFactory) {
            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setMessageConverter(messageConverter());

            factory.setDefaultRequeueRejected(false);

            RetryTemplate retryTemplate = new RetryTemplate();

            SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
            retryPolicy.setMaxAttempts(3);

            FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
            backOffPolicy.setBackOffPeriod(1000); // 1s

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        factory.setRetryTemplate(retryTemplate);



            return factory;
        }

        @Bean
        public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
            return new RabbitAdmin(connectionFactory);
        }



}
