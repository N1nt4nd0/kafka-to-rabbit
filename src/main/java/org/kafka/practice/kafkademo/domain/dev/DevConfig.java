package org.kafka.practice.kafkademo.domain.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("full-cycle-dev")
@Configuration
public class DevConfig {

    @Value("${dev.kafka-producer-delay-ms}")
    private int kafkaProducerDelayMs;

    @Bean
    public int kafkaProducerDelayMs() {
        return kafkaProducerDelayMs;
    }

}
