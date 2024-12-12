package org.kafka.practice.kafkademo.domain.entities.mappers;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTORequest toPersonDtoRequest(Person person) {
        return new PersonDTORequest(person.getId(), person.getEmail(), person.getFirstName(), person.getLastName());
    }

    @Override
    public Person fromPersonDtoRequest(PersonDTORequest request) {
        return new Person(request.getId(), request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTOResponse toPersonDtoResponse(Person person) {
        return new PersonDTOResponse(person.getId(), person.getEmail(), person.getFirstName(), person.getLastName());
    }

    @Override
    public Person fromPersonDtoResponse(PersonDTOResponse response) {
        return new Person(response.getId(), response.getEmail(), response.getFirstName(), response.getLastName());
    }

    @Override
    public PersonDTOResponse personDtoRequestToPersonDtoResponse(PersonDTORequest request) {
        return new PersonDTOResponse(request.getId(), request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTORequest clonePersonDtoRequest(PersonDTORequest request) {
        return new PersonDTORequest(request.getId(), request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTOResponse clonePersonDtoResponse(PersonDTOResponse response) {
        return new PersonDTOResponse(response.getId(), response.getEmail(), response.getFirstName(), response.getLastName());
    }

}
