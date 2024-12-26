package org.kafka.practice.kafkademo.domain.mappers;

import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Hobby;

public interface HobbyMapper {

    HobbyDtoOut toHobbyDtoOut(Hobby hobby);

}
