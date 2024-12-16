package org.kafka.practice.kafkademo.domain.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.business.service.PersonDtoRedirectBusinessService;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitDataListener {

    private final PersonDtoRedirectBusinessService personDtoRedirectBusinessService;

    @RabbitListener(queues = "#{@personDtoRabbitResponseQueueName}")
    public void rabbitPersonDtoResponseListener(final PersonDTOResponse response) {
        log.debug("Received PersonDtoResponse from rabbit: {}", response);
        personDtoRedirectBusinessService.receivePersonDtoResponseFromRabbit(response);
    }

}
