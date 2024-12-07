package org.kafka.practice.kafkademo.domain.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${messaging.kafka.group-id}")
    private String groupId;

    @Value("${messaging.kafka.receive-topic-name}")
    private String receiveTopicName;

    @Value("${messaging.kafka.response-topic-name}")
    private String responseTopicName;

    @Bean
    public String kafkaGroupId() {
        return groupId;
    }

    @Bean
    public String receiveTopicName() {
        return receiveTopicName;
    }

    @Bean
    public String responseTopicName() {
        return responseTopicName;
    }

}
