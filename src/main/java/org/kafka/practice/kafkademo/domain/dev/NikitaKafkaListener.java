package org.kafka.practice.kafkademo.domain.dev;

import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("full-cycle-dev")
@Slf4j
@Service
public class NikitaKafkaListener {

    @KafkaListener(topics = "#{@responseTopicName}", groupId = "#{@kafkaGroupId}")
    public void receiveKafkaPersonDto(final PersonDTO personDTO) {
        log.info("Receive kafka PersonDTO as Nikita: {}", personDTO);
    }

}
