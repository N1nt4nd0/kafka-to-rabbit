package org.kafka.practice.kafkademo.domain.entities.generation;

import org.kafka.practice.kafkademo.domain.entities.value.PersonDTO;
import org.kafka.practice.kafkademo.domain.entities.value.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    PersonDTO toPersonDto(Person person);

    Person fromPersonDto(PersonDTO personDTO);

}
