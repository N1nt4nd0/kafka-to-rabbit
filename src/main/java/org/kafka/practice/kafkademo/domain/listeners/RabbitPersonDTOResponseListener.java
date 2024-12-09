package org.kafka.practice.kafkademo.domain.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.service.RedirectService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitPersonDTOResponseListener {

    private final RedirectService redirectService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ""), // TODO make named queue
            exchange = @Exchange(value = "#{@rabbitResponseExchangeName}", type = "topic"),
            key = "#{@rabbitRoutingKey}"
    ))
    public void receiveRabbitPersonDtoResponse(final PersonDTOResponse response) {
        log.debug("Received PersonDTOResponse from rabbit: {}", response);
        redirectService.receivePersonDtoResponseFromRabbit(response);
    }

}
