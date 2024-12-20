package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfig {

    @Value("${web.pages.endpoints.persons-list-path}")
    private String personsListPath;

    @Value("${web.pages.endpoints.companies-list-path}")
    private String companiesListPath;

    @Value("${web.rest-api.endpoints.persons-list-api}")
    private String personsListApiPath;

    @Value("${web.rest-api.endpoints.companies-list-api}")
    private String companiesListApiPath;

    @Bean
    public String personsListPath() {
        return personsListPath;
    }

    @Bean
    public String companiesListPath() {
        return companiesListPath;
    }

    @Bean
    public String personsListApiPath() {
        return personsListApiPath;
    }

    @Bean
    public String companiesListApiPath() {
        return companiesListApiPath;
    }

}
