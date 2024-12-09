package org.kafka.practice.kafkademo.domain.config;

import org.springframework.amqp.core.TopicExchange;
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

    @Value("${messaging.rabbit.person-dto-routing-key}")
    private String personDtoRoutingKey;

    @Bean
    public String personDtoRabbitRoutingKey() {
        return personDtoRoutingKey;
    }

    @Bean
    public String personDtoRedirectRabbitExchange() {
        return personDtoRedirectExchangeName;
    }

    @Bean
    public String personDtoResponseRabbitExchange() {
        return personDtoResponseExchangeName;
    }

    @Bean
    public TopicExchange personDtoRedirectTopicExchange() {
        return new TopicExchange(personDtoRedirectExchangeName, true, false);
    }

    @Bean
    public TopicExchange personDtoResponseTopicExchange() {
        return new TopicExchange(personDtoResponseExchangeName, true, false);
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

}