package org.kafka.practice.kafkademo.domain.config;

import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${random-exception-generator.exception-probability-percent}")
    private int exceptionProbability;

    @Bean
    public ExceptionGenerator exceptionGenerator() {
        return new ExceptionGenerator(exceptionProbability);
    }

}
