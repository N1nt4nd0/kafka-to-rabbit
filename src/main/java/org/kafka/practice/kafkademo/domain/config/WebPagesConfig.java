package org.kafka.practice.kafkademo.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPagesConfig {

    @Value("${web.pages.update-interval-ms}")
    private int pageUpdateIntervalMs;

    @Value("${web.pages.page-max-elements-size}")
    private int pageMaxElementsSize;

    @Bean
    public int pageUpdateIntervalMs() {
        return pageUpdateIntervalMs;
    }

    @Bean
    public int pageMaxElementsSize() {
        return pageMaxElementsSize;
    }

}
