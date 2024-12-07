package org.kafka.practice.kafkademo.domain.config;

import org.kafka.practice.kafkademo.domain.utils.RandomErrorGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${random-error-generator.error-probability}")
    private int randomErrorProbability;

    @Bean
    public RandomErrorGenerator randomErrorGenerator() {
        return new RandomErrorGenerator(randomErrorProbability);
    }

}
