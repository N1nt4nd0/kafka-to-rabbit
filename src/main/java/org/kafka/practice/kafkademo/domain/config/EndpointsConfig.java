package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfig {

    @Value("${web.endpoints.persons-list-path}")
    private String personsListEndpointPath;

    @Value("${web.endpoints.companies-list-path}")
    private String companiesListEndpointPath;

    @Bean
    public String personsListEndpointPath() {
        return personsListEndpointPath;
    }

    @Bean
    public String companiesListEndpointPath() {
        return companiesListEndpointPath;
    }

}
