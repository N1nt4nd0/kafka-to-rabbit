package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitListenerService {

    private final RedirectService redirectService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ""), //TODO make named queue
            exchange = @Exchange(value = "#{@rabbitResponseExchangeName}", type = "topic"),
            key = "#{@rabbitRoutingKey}"
    ))
    public void receiveRabbitPersonDto(final PersonDTO personDto) {
        redirectService.receivePersonDtoFromRabbit(personDto);
    }

}
