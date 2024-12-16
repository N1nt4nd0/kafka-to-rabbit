package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfig {

    @Value("${web.pages.endpoints.persons-list-path}")
    private String personsListEndpointPath;

    @Value("${web.pages.endpoints.companies-list-path}")
    private String companiesListEndpointPath;

    @Value("${web.rest-api.endpoints.persons-list-api}")
    private String personsListApiEndpointPath;

    @Value("${web.rest-api.endpoints.companies-list-api}")
    private String companiesListApiEndpointPath;

    @Bean
    public String personsListEndpointPath() {
        return personsListEndpointPath;
    }

    @Bean
    public String companiesListEndpointPath() {
        return companiesListEndpointPath;
    }

    @Bean
    public String personsListApiEndpointPath() {
        return personsListApiEndpointPath;
    }

    @Bean
    public String companiesListApiEndpointPath() {
        return companiesListApiEndpointPath;
    }

}
