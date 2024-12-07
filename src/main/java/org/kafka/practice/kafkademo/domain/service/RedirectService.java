package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.kafka.practice.kafkademo.domain.utils.RandomErrorGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedirectService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    private final RandomErrorGenerator randomErrorGenerator;

    private final String redirectExchangeName;
    private final String rabbitRoutingKey;

    private final String responseTopicName;

    public void receivePersonDtoFromKafka(final PersonDTO personDTO) {
        log.info("====================================================");
        log.info("Received personDTO from kafka {}", personDTO);
        randomErrorGenerator.generate();
        log.info("Redirect personDTO to rabbit {}", personDTO);
        redirectPersonDtoToRabbit(personDTO);
    }

    public void redirectPersonDtoToRabbit(final PersonDTO personDTO) {
        rabbitTemplate.convertAndSend(redirectExchangeName, rabbitRoutingKey, personDTO);
    }

    public void receivePersonDtoFromRabbit(final PersonDTO personDTO) {
        log.info("Received PersonDTO from rabbit: {}", personDTO);
        sendPersonDtoKafkaResponse(personDTO);
    }
    
    public void sendPersonDtoKafkaResponse(final PersonDTO personDTO) {
        kafkaTemplate.send(responseTopicName, personDTO);
    }

}
