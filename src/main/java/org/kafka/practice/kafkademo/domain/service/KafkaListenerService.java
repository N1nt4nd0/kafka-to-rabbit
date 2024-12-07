package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final RedirectService redirectService;

    @KafkaListener(topics = "#{@receiveTopicName}", groupId = "#{@kafkaGroupId}")
    public void receiveKafkaPersonDto(final PersonDTO personDTO) {
        redirectService.receivePersonDtoFromKafka(personDTO);
    }

}
