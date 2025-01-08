package org.kafka.practice.kafkademo.domain.mappers;

import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.stereotype.Component;

@Component
public class HobbyMapperImpl implements HobbyMapper {

    @Override
    public HobbyDtoOut toHobbyDtoOut(final Hobby hobby) {
        return new HobbyDtoOut(hobby.getId().toHexString(), hobby.getHobbyName());
    }

}
