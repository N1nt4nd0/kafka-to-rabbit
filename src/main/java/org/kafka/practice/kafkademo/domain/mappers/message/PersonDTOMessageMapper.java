package org.kafka.practice.kafkademo.domain.mappers.message;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;

public interface PersonDTOMessageMapper {

    PersonDTOResponse personDtoRequestToPersonDtoResponse(PersonDTORequest request);

    PersonDTORequest clonePersonDtoRequest(PersonDTORequest request);

    PersonDTOResponse clonePersonDtoResponse(PersonDTOResponse request);

}
