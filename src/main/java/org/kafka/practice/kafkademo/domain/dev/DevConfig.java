package org.kafka.practice.kafkademo.domain.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("full-cycle-dev")
@Configuration
public class DevConfig {

    @Value("${dev.nikita-kafka-producer-delay-ms}")
    private int producerDelayMs;

    @Bean
    public int nikitaKafkaProducerDelayMs() {
        return producerDelayMs;
    }

}
