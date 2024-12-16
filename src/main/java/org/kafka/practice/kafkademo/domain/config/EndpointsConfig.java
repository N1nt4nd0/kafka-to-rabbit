package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfig {

    @Value("${web.endpoints.persons-list-path}")
    private String personsListEndpointPath;

    @Value("${web.endpoints.jobs-list-path}")
    private String jobsListEndpointPath;

    @Bean
    public String personsListEndpointPath() {
        return personsListEndpointPath;
    }

    @Bean
    public String jobsListEndpointPath() {
        return jobsListEndpointPath;
    }

}
