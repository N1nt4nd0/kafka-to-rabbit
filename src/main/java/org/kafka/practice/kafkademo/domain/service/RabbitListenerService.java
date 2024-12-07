package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitListenerService {

    private final RedirectService redirectService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ""),
            exchange = @Exchange(value = "#{@responseExchangeName}", type = "topic"),
            key = "#{@rabbitRoutingKey}"
    ))
    public void receiveRabbitPersonDto(final PersonDTO personDTO) {
        redirectService.receivePersonDtoFromRabbit(personDTO);
    }

}
