package org.kafka.practice.kafkademo.domain.business.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.dto.mappers.message.PersonDTOMessageMapper;
import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonDtoRedirectBusinessServiceImpl implements PersonDtoRedirectBusinessService {

    private final ExceptionGenerator personDtoReceiveExceptionGenerator;
    private final PersonDTOMessageMapper personDTOMessageMapper;
    private final PersonBusinessService personBusinessService;

    private final String personDtoRabbitExchangeName;
    private final String personDtoRabbitRedirectKey;

    private final String personDtoKafkaResponseTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public void receivePersonDtoRequestFromKafka(@NonNull final PersonDTORequest request) {
        final var personDtoIn = personDTOMessageMapper.personDtoRequestToPersonDtoIn(request);
        final var personDtoOut = personBusinessService.updateOrCreate(personDtoIn);
        personDtoReceiveExceptionGenerator.generateRandomException();
        log.debug("Person successfully created: {}", personDtoOut);
        final var clonedRequest = personDTOMessageMapper.clonePersonDtoRequest(request);
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
            personBusinessService.deleteByEmail(response.getEmail());
        }
        final var clonedResponse = personDTOMessageMapper.clonePersonDtoResponse(response);
        sendPersonDtoResponseToKafka(clonedResponse);
    }

    @Override
    public void sendPersonDtoResponseToKafka(@NonNull final PersonDTOResponse response) {
        kafkaTemplate.send(personDtoKafkaResponseTopic, response);
        log.debug("PersonDtoResponse sent to kafka: {}", response);
    }

}
