package org.kafka.practice.kafkademo.domain.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.mappers.PersonDtoMapper;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.service.entities.PersonService;
import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonDtoRedirectService {

    private final ExceptionGenerator exceptionGenerator;
    private final PersonDtoMapper personDtoMapper;
    private final PersonService personService;

    private final String personDtoRedirectRabbitExchange;
    private final String personDtoRabbitRoutingKey;

    private final String personDtoKafkaResponseTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void receivePersonDtoRequestFromKafka(@NonNull final PersonDTORequest request) {
        final var person = personDtoMapper.fromPersonDtoRequest(request);
        log.debug("Starting to save person at database");
        final var savedPerson = personService.savePerson(person);
        exceptionGenerator.generateRandomException();
        log.debug("Person saved successfully. Person: {}", savedPerson);
        final var clonedRequest = personDtoMapper.clonePersonDtoRequest(request);
        redirectPersonDtoRequestToRabbit(clonedRequest);
    }

    public void redirectPersonDtoRequestToRabbit(@NonNull final PersonDTORequest request) {
        rabbitTemplate.convertAndSend(personDtoRedirectRabbitExchange, personDtoRabbitRoutingKey, request);
        log.debug("PersonDtoRequest redirected to rabbit: {}", request);
    }

    public void receivePersonDtoResponseFromRabbit(@NonNull final PersonDTOResponse response) {
        if (response.isFail()) {
            final var person = personDtoMapper.fromPersonDtoResponse(response);
            log.debug("PersonDtoResponse failed. Deleting person from database");
            personService.deletePerson(person);
            log.debug("Person deleted. Person: {}", person);
        }
        final var clonedResponse = personDtoMapper.clonePersonDtoResponse(response);
        sendPersonDtoResponseToKafka(clonedResponse);
    }

    public void sendPersonDtoResponseToKafka(@NonNull final PersonDTOResponse response) {
        kafkaTemplate.send(personDtoKafkaResponseTopic, response);
        log.debug("PersonDtoResponse sent to kafka: {}", response);
    }

}
