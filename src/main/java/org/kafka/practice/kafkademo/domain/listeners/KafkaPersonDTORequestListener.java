package org.kafka.practice.kafkademo.domain.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.service.RedirectService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPersonDTORequestListener {

    private final RedirectService redirectService;

    @KafkaListener(topics = "#{@kafkaReceiveTopicName}", groupId = "#{@kafkaGroupId}")
    public void receiveKafkaPersonDtoRequest(final PersonDTORequest request) {
        log.debug("============================================================");
        log.debug("Received PersonDTORequest from kafka {}", request);
        redirectService.receivePersonDtoRequestFromKafka(request);
    }

}
