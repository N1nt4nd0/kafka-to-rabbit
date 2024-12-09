package org.kafka.practice.kafkademo.domain.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.mappers.PersonDtoMapper;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.service.PersonDTORedirectService;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaErrorHandler {

    private final PersonDTORedirectService personDtoRedirectService;
    private final PersonDtoMapper personDtoMapper;

    @Bean
    public DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler((record, exception) -> {
            final Throwable cause;
            if (exception.getCause() == null) {
                cause = exception;
            } else {
                cause = exception.getCause();
            }
            final var value = record.value();
            log.debug("Kafka error occurred: {}", cause.toString());
            log.trace("Error details:", cause);
            if (value instanceof PersonDTORequest request) {
                log.debug("PersonDTORequest failed by {}. Request: {}", cause, request);
                final var response = personDtoMapper.personDtoRequestToPersonDtoResponse(request);
                response.setFail(true);
                personDtoRedirectService.sendPersonDtoResponseToKafka(response);
                log.debug("Sent fail PersonDTOResponse to kafka");
            }
        }, new FixedBackOff(0L, 0L));
    }

}
