package org.kafka.practice.kafkademo.domain.config.web;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class WebPagesConfig {

    @Value("${web.pages.update-interval-ms}")
    private int pageUpdateIntervalMs;

    @Value("${web.pages.page-max-elements-size}")
    private int pageMaxElementsSize;

    @Value("${web.pages.page-default-elements-size}")
    private int pageDefaultElementsSize;

}
