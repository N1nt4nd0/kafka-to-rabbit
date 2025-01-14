package org.kafka.practice.kafkademo.domain.config.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${messaging.kafka.person-dto-group-id}")
    private String personDtoGroupId;

    @Value("${messaging.kafka.person-dto-receive-topic-name}")
    private String personDtoReceiveTopicName;

    @Value("${messaging.kafka.person-dto-response-topic-name}")
    private String personDtoResponseTopicName;

    @Bean
    public String personDtoKafkaGroupId() {
        return personDtoGroupId;
    }

    @Bean
    public String personDtoKafkaReceiveTopic() {
        return personDtoReceiveTopicName;
    }

    @Bean
    public String personDtoKafkaResponseTopic() {
        return personDtoResponseTopicName;
    }

}
