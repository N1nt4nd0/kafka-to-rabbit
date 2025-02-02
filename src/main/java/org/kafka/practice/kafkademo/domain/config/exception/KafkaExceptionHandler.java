package org.kafka.practice.kafkademo.domain.config.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.business.service.PersonDtoRedirectService;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.mappers.message.PersonDTOMessageMapper;
import org.kafka.practice.kafkademo.domain.utils.LogHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaExceptionHandler {

    private final PersonDtoRedirectService personDtoRedirectService;
    private final PersonDTOMessageMapper personDTOMessageMapper;

    @Bean
    public DefaultErrorHandler globalKafkaErrorHandler() {
        return new DefaultErrorHandler((record, exception) -> {
            final var value = record.value();
            LogHelper.logError("Kafka error occurred", exception, log);
            if (value instanceof PersonDTORequest request) {
                log.debug("PersonDtoRequest processing failed: {}", request);
                final var response = personDTOMessageMapper.personDtoRequestToPersonDtoResponse(request);
                response.setFail(true);
                personDtoRedirectService.sendPersonDtoResponseToKafka(response);
            }
        }, new FixedBackOff(0L, 0L));
    }

}
