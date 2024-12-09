package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final RedirectService redirectService;

    @KafkaListener(topics = "#{@kafkaReceiveTopicName}", groupId = "#{@kafkaGroupId}")
    public void receiveKafkaPersonDtoRequest(final PersonDTORequest request) { //TODO Listeners to listeners package but not in service
        redirectService.receivePersonDtoRequestFromKafka(request);
    }

}
