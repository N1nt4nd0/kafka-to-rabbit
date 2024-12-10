package org.kafka.practice.kafkademo.domain.config;

import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionGeneratorConfig {

    @Value("${random-exception-generator.person-dto-receive-exception-probability-percent}")
    private int personDtoReceiveExceptionProbability;

    @Bean
    public ExceptionGenerator personDtoReceiveExceptionGenerator() {
        return new ExceptionGenerator(personDtoReceiveExceptionProbability);
    }

    @Bean
    public ExceptionGenerator fiftyExceptionGenerator() {
        return new ExceptionGenerator(50);
    }

}
