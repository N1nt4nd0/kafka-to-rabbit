package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfig {

    @Value("${endpoints.persons-list-path}")
    private String personsListEndpointPath;

    @Bean
    public String personsListEndpointPath() {
        return personsListEndpointPath;
    }

}
