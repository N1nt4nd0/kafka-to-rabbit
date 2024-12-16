package org.kafka.practice.kafkademo.domain.dto.mappers.message;

import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;

public interface PersonDTOMessageMapper {

    PersonDTORequest toPersonDtoRequest(Person person);

    PersonDTOResponse personDtoRequestToPersonDtoResponse(PersonDTORequest request);

    PersonDTORequest clonePersonDtoRequest(PersonDTORequest request);

    PersonDTOResponse clonePersonDtoResponse(PersonDTOResponse request);

    PersonDtoIn personDtoRequestToPersonDtoIn(PersonDTORequest personDTORequest);

}
