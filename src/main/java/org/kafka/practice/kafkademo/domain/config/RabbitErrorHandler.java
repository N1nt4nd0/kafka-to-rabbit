package org.kafka.practice.kafkademo.domain.config;

import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.utils.LogHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class RabbitErrorHandler {

    @Bean
    public ErrorHandler globalRabbitErrorHandler() {
        return throwable -> LogHelper.logError("Rabbit error occurred", throwable, log);
    }

}
