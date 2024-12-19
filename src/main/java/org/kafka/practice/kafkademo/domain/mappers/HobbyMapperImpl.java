package org.kafka.practice.kafkademo.domain.mappers;

import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.stereotype.Component;

@Component
public class HobbyMapperImpl implements HobbyMapper {

    @Override
    public HobbyDtoOut toHobbyDtoOut(final Hobby hobby) {
        return new HobbyDtoOut(hobby.getId(), hobby.getHobbyName());
    }

    @Override
    public Hobby fromPersonAddHobbyDto(final AddPersonHobbyDtoIn addPersonHobbyDto) {
        return Hobby.blankHobby(addPersonHobbyDto.getHobbyName());
    }

    @Override
    public Hobby fromPersonRemoveHobbyDto(final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        return new Hobby(null, removePersonHobbyDto.getHobbyName());
    }

}
