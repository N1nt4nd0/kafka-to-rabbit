package org.kafka.practice.kafkademo.domain.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitConfig {

    @Value("${messaging.rabbit.person-dto-exchange-name}")
    private String personDtoExchangeName;

    @Value("${messaging.rabbit.person-dto-redirect-queue-name}")
    private String personDtoRedirectQueueName;

    @Value("${messaging.rabbit.person-dto-response-queue-name}")
    private String personDtoResponseQueueName;

    @Value("${messaging.rabbit.person-dto-redirect-routing-key}")
    private String personDtoRedirectKey;

    @Value("${messaging.rabbit.person-dto-response-routing-key}")
    private String personDtoResponseKey;

    @Bean
    public String personDtoRabbitExchangeName() {
        return personDtoExchangeName;
    }

    @Bean
    public String personDtoRabbitRedirectQueueName() {
        return personDtoRedirectQueueName;
    }

    @Bean
    public String personDtoRabbitResponseQueueName() {
        return personDtoResponseQueueName;
    }

    @Bean
    public String personDtoRabbitRedirectKey() {
        return personDtoRedirectKey;
    }

    @Bean
    public String personDtoRabbitResponseKey() {
        return personDtoResponseKey;
    }

    @Bean
    public Jackson2JsonMessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                         final Jackson2JsonMessageConverter messageConverter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange personDtoExchange() {
        return ExchangeBuilder.directExchange(personDtoExchangeName).durable(true).build();
    }

    @Bean
    public Queue personDtoRedirectQueue() {
        return QueueBuilder.durable(personDtoRedirectQueueName).build();
    }

    @Bean
    public Queue personDtoResponseQueue() {
        return QueueBuilder.durable(personDtoResponseQueueName).build();
    }

    @Bean
    public Binding bindPersonDtoRedirectQueue(final Queue personDtoRedirectQueue,
                                              final DirectExchange personDtoExchange) {
        return BindingBuilder
                .bind(personDtoRedirectQueue)
                .to(personDtoExchange)
                .with(personDtoRedirectKey);
    }

    @Bean
    public Binding bindPersonDtoResponseQueue(final Queue personDtoResponseQueue,
                                              final DirectExchange personDtoExchange) {
        return BindingBuilder
                .bind(personDtoResponseQueue)
                .to(personDtoExchange)
                .with(personDtoResponseKey);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitFactory(final ConnectionFactory connectionFactory,
                                                              final Jackson2JsonMessageConverter rabbitMessageConverter,
                                                              final ErrorHandler globalRabbitErrorHandler) {
        final var factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(rabbitMessageConverter);
        factory.setErrorHandler(globalRabbitErrorHandler);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}