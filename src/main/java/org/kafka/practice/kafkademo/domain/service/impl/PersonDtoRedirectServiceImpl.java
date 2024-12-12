package org.kafka.practice.kafkademo.domain.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.mappers.PersonMapper;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.service.PersonDtoRedirectService;
import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonDtoRedirectServiceImpl implements PersonDtoRedirectService {

    private final ExceptionGenerator personDtoReceiveExceptionGenerator;
    private final PersonServiceImpl personService;
    private final PersonMapper personMapper;

    private final String personDtoRabbitExchangeName;
    private final String personDtoRabbitRedirectKey;

    private final String personDtoKafkaResponseTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void receivePersonDtoRequestFromKafka(@NonNull final PersonDTORequest request) {
        final var createdPerson = personService.updateOrCreate(request.getEmail(),
                request.getFirstName(), request.getLastName());
        personDtoReceiveExceptionGenerator.generateRandomException();
        log.debug("Person successfully created: {}", createdPerson);
        final var clonedRequest = personMapper.clonePersonDtoRequest(request);
        redirectPersonDtoRequestToRabbit(clonedRequest);
    }

    @Override
    public void redirectPersonDtoRequestToRabbit(@NonNull final PersonDTORequest request) {
        rabbitTemplate.convertAndSend(personDtoRabbitExchangeName, personDtoRabbitRedirectKey, request);
        log.debug("PersonDtoRequest redirected to rabbit: {}", request);
    }

    @Override
    public void receivePersonDtoResponseFromRabbit(@NonNull final PersonDTOResponse response) {
        if (response.isFail()) {
            log.debug("PersonDtoResponse failed. Deleting person from database");
            personService.deleteByEmail(response.getEmail());
        }
        final var clonedResponse = personMapper.clonePersonDtoResponse(response);
        sendPersonDtoResponseToKafka(clonedResponse);
    }

    @Override
    public void sendPersonDtoResponseToKafka(@NonNull final PersonDTOResponse response) {
        kafkaTemplate.send(personDtoKafkaResponseTopic, response);
        log.debug("PersonDtoResponse sent to kafka: {}", response);
    }

}
