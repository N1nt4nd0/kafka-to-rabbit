package org.kafka.practice.kafkademo.domain.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class EndpointsConfig {

    @Value("${web.rest-api.endpoints.person-list}")
    private String personListApiPath;

    @Value("${web.rest-api.endpoints.person-fill}")
    private String personFillApiPath;

    @Value("${web.rest-api.endpoints.person-truncate}")
    private String personTruncateApiPath;

    @Value("${web.rest-api.endpoints.company-list}")
    private String companyListApiPath;

    @Value("${web.rest-api.endpoints.company-fill}")
    private String companyFillApiPath;

    @Value("${web.rest-api.endpoints.company-truncate}")
    private String companyTruncateApiPath;

    @Value("${web.rest-api.endpoints.hobby-list}")
    private String hobbyListApiPath;

    @Value("${web.rest-api.endpoints.hobby-fill}")
    private String hobbyFillApiPath;

    @Value("${web.rest-api.endpoints.hobby-truncate}")
    private String hobbyTruncateApiPath;

}
