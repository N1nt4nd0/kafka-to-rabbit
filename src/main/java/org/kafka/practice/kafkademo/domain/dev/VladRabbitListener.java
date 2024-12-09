package org.kafka.practice.kafkademo.domain.dev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.mappers.PersonDtoMapper;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;
import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"full-cycle-dev", "vlad-dev"})
@Slf4j
@Service
@RequiredArgsConstructor
public class VladRabbitListener {

    private final String personDtoResponseRabbitExchange;
    private final String personDtoRabbitRoutingKey;

    private final ExceptionGenerator vladExceptionGenerator;
    private final PersonDtoMapper personDtoMapper;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = ""),
            exchange = @Exchange(value = "#{@personDtoRedirectRabbitExchange}", type = "topic"),
            key = "#{@personDtoRabbitRoutingKey}"
    ))
    public void receivePersonDtoRequest(final PersonDTORequest request) {
        log.debug("[DEV] Received rabbit PersonDtoRequest as Vlad: {}", request);
        final var response = personDtoMapper.personDtoRequestToPersonDtoResponse(request);
        try {
            vladExceptionGenerator.generateRandomException();
        } catch (RandomGeneratorException randomGeneratorException) {
            response.setFail(true);
            log.debug("[DEV] Random exception occurred at Vlad side");
        }
        rabbitTemplate.convertAndSend(personDtoResponseRabbitExchange, personDtoRabbitRoutingKey, response);
        log.debug("[DEV] PersonDtoResponse sent as Vlad: {}", response);
    }

}
