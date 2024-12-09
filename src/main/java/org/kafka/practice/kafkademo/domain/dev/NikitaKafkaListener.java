package org.kafka.practice.kafkademo.domain.dev;

import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("full-cycle-dev")
@Slf4j
@Service
public class NikitaKafkaListener {

    @KafkaListener(topics = "#{@kafkaResponseTopicName}", groupId = "#{@kafkaGroupId}")
    public void receiveKafkaPersonDtoResponse(final PersonDTOResponse response) {
        log.debug("Received kafka PersonDTOResponse as Nikita: {}", response);
    }

}
