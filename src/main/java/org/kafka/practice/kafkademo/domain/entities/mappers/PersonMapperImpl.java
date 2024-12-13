package org.kafka.practice.kafkademo.domain.entities.mappers;

import lombok.NonNull;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTORequest toPersonDtoRequest(@NonNull final Person person) {
        return new PersonDTORequest(person.getEmail(), person.getFirstName(), person.getLastName());
    }

    @Override
    public Person fromPersonDtoRequest(@NonNull final PersonDTORequest request) {
        return new Person(request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTOResponse toPersonDtoResponse(@NonNull final Person person) {
        return new PersonDTOResponse(person.getEmail(), person.getFirstName(), person.getLastName());
    }

    @Override
    public Person fromPersonDtoResponse(@NonNull final PersonDTOResponse response) {
        return new Person(response.getEmail(), response.getFirstName(), response.getLastName());
    }

    @Override
    public PersonDTOResponse personDtoRequestToPersonDtoResponse(@NonNull final PersonDTORequest request) {
        return new PersonDTOResponse(request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTORequest clonePersonDtoRequest(@NonNull final PersonDTORequest request) {
        return new PersonDTORequest(request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTOResponse clonePersonDtoResponse(@NonNull final PersonDTOResponse response) {
        return new PersonDTOResponse(response.getEmail(), response.getFirstName(), response.getLastName());
    }

}
