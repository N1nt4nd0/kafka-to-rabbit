package org.kafka.practice.kafkademo.domain.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
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
    public Binding bindQueueToPersonDtoRedirectExchange(Queue personDtoQueue,
                                                        TopicExchange personDtoRedirectTopicExchange) {
        return BindingBuilder.bind(personDtoQueue).to(personDtoRedirectTopicExchange).with(personDtoRoutingKey);
    }

    @Bean
    public Binding bindQueueToPersonDtoResponseExchange(Queue personDtoQueue,
                                                        TopicExchange personDtoResponseTopicExchange) {
        return BindingBuilder.bind(personDtoQueue).to(personDtoResponseTopicExchange).with(personDtoRoutingKey);
    }

}