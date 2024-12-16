package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;

public interface PersonDtoRedirectBusinessService {

    void receivePersonDtoRequestFromKafka(PersonDTORequest request);

    void redirectPersonDtoRequestToRabbit(PersonDTORequest request);

    void receivePersonDtoResponseFromRabbit(PersonDTOResponse response);

    void sendPersonDtoResponseToKafka(PersonDTOResponse response);

}
