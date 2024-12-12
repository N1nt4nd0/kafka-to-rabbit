package org.kafka.practice.kafkademo.domain.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.service.PersonDtoRedirectService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitDataListener {

    private final PersonDtoRedirectService personDtoRedirectService;

    @RabbitListener(queues = "#{@personDtoRabbitResponseQueueName}")
    public void rabbitPersonDtoResponseListener(final PersonDTOResponse response) {
        log.debug("Received PersonDtoResponse from rabbit: {}", response);
        personDtoRedirectService.receivePersonDtoResponseFromRabbit(response);
    }

}
