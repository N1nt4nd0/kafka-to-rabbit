package org.kafka.practice.kafkademo.domain.dto.mappers;

import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Hobby;

public interface HobbyMapper {

    HobbyDtoOut toHobbyDtoOut(Hobby hobby);

    Hobby fromPersonAddHobbyDto(AddPersonHobbyDtoIn addPersonHobbyDto);

    Hobby fromPersonRemoveHobbyDto(RemovePersonHobbyDtoIn removePersonHobbyDto);

}
