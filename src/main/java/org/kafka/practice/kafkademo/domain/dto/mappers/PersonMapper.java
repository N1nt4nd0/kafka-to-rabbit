package org.kafka.practice.kafkademo.domain.dto.mappers;

import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Person;

public interface PersonMapper {

    PersonDtoOut toPersonDtoOut(Person person);

}
