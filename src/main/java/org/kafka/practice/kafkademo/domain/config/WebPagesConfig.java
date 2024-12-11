package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPagesConfig {

    @Value("${web.page-update-interval-ms}")
    private int pageUpdateIntervalMs;

    @Bean
    public int pageUpdateIntervalMs() {
        return pageUpdateIntervalMs;
    }

}
