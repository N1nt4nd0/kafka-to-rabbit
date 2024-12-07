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

    @Value("${messaging.rabbit.redirect-exchange-name}")
    private String redirectExchangeName;

    @Value("${messaging.rabbit.response-exchange-name}")
    private String responseExchangeName;

    @Value("${messaging.rabbit.routing-key}")
    private String routingKey;

    @Bean
    public String rabbitRoutingKey() {
        return routingKey;
    }

    @Bean
    public String rabbitRedirectExchangeName() {
        return redirectExchangeName;
    }

    @Bean
    public String rabbitResponseExchangeName() {
        return responseExchangeName;
    }

    @Bean
    public TopicExchange redirectTopicExchange() {
        return new TopicExchange(redirectExchangeName, true, false);
    }

    @Bean
    public TopicExchange responseTopicExchange() {
        return new TopicExchange(responseExchangeName, true, false);
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