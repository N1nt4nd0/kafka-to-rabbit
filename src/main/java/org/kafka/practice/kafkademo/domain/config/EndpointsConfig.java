package org.kafka.practice.kafkademo.domain.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EndpointsConfig {

    @Value("${web.pages.endpoints.persons-list}")
    private String personsListPath;

    @Value("${web.pages.endpoints.companies-list}")
    private String companiesListPath;

    @Value("${web.rest-api.endpoints.persons-list}")
    private String personsListApiPath;

    @Value("${web.rest-api.endpoints.companies-list}")
    private String companiesListApiPath;

    @Value("${web.rest-api.endpoints.companies-fill}")
    private String companiesFillApiPath;

    @Value("${web.rest-api.endpoints.hobbies-fill}")
    private String hobbiesFillApiPath;

    @Value("${web.rest-api.endpoints.persons-fill}")
    private String personsFillApiPath;

}
