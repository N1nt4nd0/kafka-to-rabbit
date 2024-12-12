package org.kafka.practice.kafkademo.domain.entities.mappers;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;

public interface PersonMapper {

    PersonDTORequest toPersonDtoRequest(Person person);

    Person fromPersonDtoRequest(PersonDTORequest request);

    PersonDTOResponse toPersonDtoResponse(Person person);

    Person fromPersonDtoResponse(PersonDTOResponse response);

    PersonDTOResponse personDtoRequestToPersonDtoResponse(PersonDTORequest request);

    PersonDTORequest clonePersonDtoRequest(PersonDTORequest request);

    PersonDTOResponse clonePersonDtoResponse(PersonDTOResponse request);

}
