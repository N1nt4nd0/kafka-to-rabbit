package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPagesConfig {

    @Value("${web-pages.content-update-interval-ms}")
    private int contentUpdateIntervalMs;

    @Bean
    public int contentUpdateIntervalMs() {
        return contentUpdateIntervalMs;
    }

}
