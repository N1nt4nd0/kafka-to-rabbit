package org.kafka.practice.kafkademo.domain.dto.mappers;

import lombok.NonNull;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.stereotype.Component;

@Component
public class HobbyMapperImpl implements HobbyMapper {

    @Override
    public HobbyDtoOut toHobbyDtoOut(@NonNull final Hobby hobby) {
        return new HobbyDtoOut(hobby.getId(), hobby.getHobbyName(), hobby.getPerson().getEmail());
    }

    @Override
    public Hobby fromPersonAddHobbyDto(@NonNull final AddPersonHobbyDtoIn addPersonHobbyDto) {
        return new Hobby(addPersonHobbyDto.getHobbyName());
    }

    @Override
    public Hobby fromPersonRemoveHobbyDto(@NonNull final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        return new Hobby(removePersonHobbyDto.getHobbyName());
    }

}
