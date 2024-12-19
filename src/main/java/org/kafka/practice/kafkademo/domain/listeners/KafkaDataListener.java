package org.kafka.practice.kafkademo.domain.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.business.service.PersonDtoRedirectService;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.utils.StringUnit;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaDataListener {

    private final PersonDtoRedirectService personDtoRedirectService;

    @KafkaListener(topics = "#{@personDtoKafkaReceiveTopic}", groupId = "#{@personDtoKafkaGroupId}")
    public void kafkaPersonDtoRequestListener(final PersonDTORequest request) {
        log.debug(StringUnit.equalsRepeat());
        log.debug("Received PersonDtoRequest from kafka: {}", request);
        personDtoRedirectService.receivePersonDtoRequestFromKafka(request);
    }

}
