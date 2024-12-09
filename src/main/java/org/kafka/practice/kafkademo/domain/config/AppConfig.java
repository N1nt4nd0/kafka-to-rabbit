package org.kafka.practice.kafkademo.domain.config;

import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${random-error-generator.error-probability}")
    private int errorProbability;

    @Bean
    public ExceptionGenerator errorGenerator() {
        return new ExceptionGenerator(errorProbability);
    }

}
