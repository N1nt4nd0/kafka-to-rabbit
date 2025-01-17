package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.config.cache.CacheKeyBuilder;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.mappers.message.PersonDTOMessageMapper;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.kafka.practice.kafkademo.domain.utils.ExceptionGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonDtoRedirectServiceImpl implements PersonDtoRedirectService {

    private final ExceptionGenerator personDtoReceiveExceptionGenerator;
    private final PersonDTOMessageMapper personDTOMessageMapper;
    private final PersonService personService;

    private final String personDtoRabbitExchangeName;
    private final String personDtoRabbitRedirectKey;

    private final String personDtoKafkaResponseTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public void receivePersonDtoRequestFromKafka(final PersonDTORequest request) {
        final var person = personService.createPerson(request.getEmail(),
                request.getFirstName(), request.getLastName());
        personDtoReceiveExceptionGenerator.generateRandomException();
        log.debug("Person successfully created: {}", person);
        final var clonedRequest = personDTOMessageMapper.clonePersonDtoRequest(request);
        redirectPersonDtoRequestToRabbit(clonedRequest);
    }

    @Override
    public void redirectPersonDtoRequestToRabbit(final PersonDTORequest request) {
        rabbitTemplate.convertAndSend(personDtoRabbitExchangeName, personDtoRabbitRedirectKey, request);
        log.debug("PersonDtoRequest redirected to rabbit: {}", request);
    }

    @Override
    @CacheEvict(
            cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME,
            condition = "#response.isFail()",
            allEntries = true)
    public void receivePersonDtoResponseFromRabbit(final PersonDTOResponse response) {
        final var clonedResponse = personDTOMessageMapper.clonePersonDtoResponse(response);
        sendPersonDtoResponseToKafka(clonedResponse);
        if (response.isFail()) {
            log.debug("PersonDtoResponse failed. Deleting person from database");
            personService.deleteByEmail(response.getEmail());
        }
    }

    @Override
    public void sendPersonDtoResponseToKafka(final PersonDTOResponse response) {
        kafkaTemplate.send(personDtoKafkaResponseTopic, response);
        log.debug("PersonDtoResponse sent to kafka: {}", response);
    }

}
