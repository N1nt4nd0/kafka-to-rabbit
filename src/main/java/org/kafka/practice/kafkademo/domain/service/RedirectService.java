package org.kafka.practice.kafkademo.domain.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.generation.PersonMapper;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.kafka.practice.kafkademo.domain.service.entities.PersonService;
import org.kafka.practice.kafkademo.domain.utils.ErrorGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedirectService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    private final PersonService personService;
    private final PersonMapper personMapper;

    private final ErrorGenerator errorGenerator;

    private final String rabbitRedirectExchangeName;
    private final String rabbitRoutingKey;

    private final String kafkaResponseTopicName;

    @Transactional
    public void receivePersonDtoFromKafka(@NonNull final PersonDTO personDto) {
        log.debug("Received personDto from kafka {}", personDto);
        if (personDto.isFail()) {
            throw new RuntimeException("The operation has already failed");
        }
        final var person = personMapper.fromPersonDto(personDto);
        log.debug("Start saving person to database");
        personService.savePerson(person);
        errorGenerator.process();
        log.debug("Person saved to database");
        redirectPersonDtoToRabbit(personDto); // TODO send DTO copy
    }

    public void redirectPersonDtoToRabbit(@NonNull final PersonDTO personDto) {
        rabbitTemplate.convertAndSend(rabbitRedirectExchangeName, rabbitRoutingKey, personDto);
        log.debug("Redirecting personDto to rabbit {}", personDto);
    }

    public void receivePersonDtoFromRabbit(@NonNull final PersonDTO personDto) {
        log.debug("Received PersonDto from rabbit: {}", personDto);
        if (personDto.isFail()) {
            final var person = personMapper.fromPersonDto(personDto);
            personService.deletePerson(person);
            log.debug("PersonDto is failed. Person removed from database");
        }
        sendPersonDtoKafkaResponse(personDto);
    }

    public void sendPersonDtoKafkaResponse(@NonNull final PersonDTO personDto) {
        kafkaTemplate.send(kafkaResponseTopicName, personDto);
        log.debug("Sending PersonDto to kafka response: {}", personDto);
    }

}
