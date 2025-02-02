package org.kafka.practice.kafkademo.domain.mappers;

import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Person;

public interface PersonMapper {

    PersonDtoOut toPersonDtoOut(Person person);

}
