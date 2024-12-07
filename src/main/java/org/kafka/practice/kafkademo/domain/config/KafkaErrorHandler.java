package org.kafka.practice.kafkademo.domain.config;

import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.kafka.practice.kafkademo.domain.service.RedirectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            log.error("Kafka error occurred: {}", cause.getMessage());
            log.trace("Error details:", cause);
            final var value = record.value();
            if (value instanceof PersonDTO personDTO) {
                personDTO.setFail(true);
                redirectService.sendPersonDtoKafkaResponse(personDTO);
            } else {
                redirectService.sendPersonDtoKafkaResponse(PersonDTO.builder().fail(true).build());
            }
        }, new FixedBackOff(0L, 0L));
    }

}
