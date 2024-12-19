package org.kafka.practice.kafkademo.domain.dto.mappers.message;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonDTOMessageMapperImpl implements PersonDTOMessageMapper {

    @Override
    public PersonDTORequest toPersonDtoRequest(final Person person) {
        return new PersonDTORequest(person.getEmail(), person.getFirstName(), person.getLastName());
    }

    @Override
    public PersonDTOResponse personDtoRequestToPersonDtoResponse(final PersonDTORequest request) {
        return new PersonDTOResponse(request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTORequest clonePersonDtoRequest(final PersonDTORequest request) {
        return new PersonDTORequest(request.getEmail(), request.getFirstName(), request.getLastName());
    }

    @Override
    public PersonDTOResponse clonePersonDtoResponse(final PersonDTOResponse response) {
        return new PersonDTOResponse(response.getEmail(), response.getFirstName(), response.getLastName());
    }

}
