package org.kafka.practice.kafkademo.domain.dev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("full-cycle-dev")
@Slf4j
@Service
@RequiredArgsConstructor
public class VladRabbitListener {

    private final RabbitTemplate rabbitTemplate;
    private final String responseExchangeName;
    private final String rabbitRoutingKey;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ""),
            exchange = @Exchange(value = "#{@redirectExchangeName}", type = "topic"),
            key = "#{@rabbitRoutingKey}"
    ))
    public void rabbitPersonDtoReceive(final PersonDTO personDTO) {
        log.info("Receive rabbit PersonDTO as Vlad: {}", personDTO);
        rabbitTemplate.convertAndSend(responseExchangeName, rabbitRoutingKey, personDTO);
    }

}
