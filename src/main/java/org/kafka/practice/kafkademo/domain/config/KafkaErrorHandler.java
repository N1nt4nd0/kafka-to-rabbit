package org.kafka.practice.kafkademo.domain.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.kafka.practice.kafkademo.domain.exception.CustomKafkaException;
import org.kafka.practice.kafkademo.domain.service.RedirectService;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaErrorHandler {

    private final RedirectService redirectService;

    @Bean
    public DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler((record, exception) -> {
            final var cause = exception.getCause() != null ? exception.getCause() : exception;
            final var value = record.value();
            log.error("Kafka error occurred: {}", cause.getMessage());
            log.trace("Error details:", cause);
            if (cause instanceof CustomKafkaException &&
                    value instanceof PersonDTO personDto) {
                personDto.setFail(true);
                log.debug("PersonDto is failed. Sending fail response to kafka");
                redirectService.sendPersonDtoKafkaResponse(personDto);
            }
        }, new FixedBackOff(0L, 0L));
    }

}
