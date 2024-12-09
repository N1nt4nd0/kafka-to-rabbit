package org.kafka.practice.kafkademo.domain.dev;

import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("full-cycle-dev")
@Configuration
public class DevConfig {

    @Value("${dev.nikita-kafka-producer-delay-ms}")
    private int producerDelayMs;

    @Value("${dev.vlad-exception-generator-probability-percent}")
    private int exceptionGeneratorProbability;

    @Bean
    public int nikitaKafkaProducerDelayMs() {
        return producerDelayMs;
    }

    @Bean
    public ExceptionGenerator fiftyExceptionGenerator() {
        return new ExceptionGenerator(50);
    }

    @Bean
    public ExceptionGenerator vladExceptionGenerator() {
        return new ExceptionGenerator(exceptionGeneratorProbability);
    }

}
