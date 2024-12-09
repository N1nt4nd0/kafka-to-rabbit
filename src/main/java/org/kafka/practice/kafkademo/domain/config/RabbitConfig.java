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

    @Value("${messaging.rabbit.person-dto-redirect-exchange-name}")
    private String personDtoRedirectExchangeName;

    @Value("${messaging.rabbit.person-dto-response-exchange-name}")
    private String personDtoResponseExchangeName;

    @Value("${messaging.rabbit.person-dto-queue-name}")
    private String personDtoQueueName;

    @Value("${messaging.rabbit.person-dto-routing-key}")
    private String personDtoRoutingKey;

    @Bean
    public String personDtoRedirectRabbitExchange() {
        return personDtoRedirectExchangeName;
    }

    @Bean
    public String personDtoResponseRabbitExchange() {
        return personDtoResponseExchangeName;
    }

    @Bean
    public String personDtoRabbitQueue() {
        return personDtoQueueName;
    }

    @Bean
    public String personDtoRabbitRoutingKey() {
        return personDtoRoutingKey;
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
    public Queue personDtoQueue() {
        return QueueBuilder.durable(personDtoQueueName).build();
    }

    @Bean
    public TopicExchange personDtoRedirectTopicExchange() {
        return ExchangeBuilder.topicExchange(personDtoRedirectExchangeName).durable(true).build();
    }

    @Bean
    public TopicExchange personDtoResponseTopicExchange() {
        return ExchangeBuilder.topicExchange(personDtoResponseExchangeName).durable(true).build();
    }

    @Bean
    public Binding bindQueueToPersonDtoRedirectExchange(final Queue personDtoQueue,
                                                        final TopicExchange personDtoRedirectTopicExchange) {
        return BindingBuilder.bind(personDtoQueue).to(personDtoRedirectTopicExchange).with(personDtoRoutingKey);
    }

    @Bean
    public Binding bindQueueToPersonDtoResponseExchange(final Queue personDtoQueue,
                                                        final TopicExchange personDtoResponseTopicExchange) {
        return BindingBuilder.bind(personDtoQueue).to(personDtoResponseTopicExchange).with(personDtoRoutingKey);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerFactory(final ConnectionFactory connectionFactory,
                                                                      final Jackson2JsonMessageConverter rabbitMessageConverter,
                                                                      final ErrorHandler globalRabbitErrorHandler) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(rabbitMessageConverter);
        factory.setErrorHandler(globalRabbitErrorHandler);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

}