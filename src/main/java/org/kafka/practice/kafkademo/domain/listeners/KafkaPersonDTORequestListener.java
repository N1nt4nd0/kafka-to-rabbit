package org.kafka.practice.kafkademo.domain.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.service.PersonDTORedirectService;
import org.kafka.practice.kafkademo.domain.utils.StringUnit;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPersonDTORequestListener {

    private final PersonDTORedirectService personDtoRedirectService;

    @KafkaListener(topics = "#{@personDtoKafkaReceiveTopic}", groupId = "#{@personDtoKafkaGroupId}")
    public void receiveKafkaPersonDtoRequest(final PersonDTORequest request) {
        log.debug(StringUnit.equalsRepeat());
        log.debug("Received PersonDTORequest from kafka: {}", request);
        personDtoRedirectService.receivePersonDtoRequestFromKafka(request);
    }

}
