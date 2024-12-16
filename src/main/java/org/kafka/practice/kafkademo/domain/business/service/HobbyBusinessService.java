package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.HobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyBusinessService {

    Page<HobbyDtoOut> getHobbies(Pageable pageable);

    HobbyDtoOut createHobby(HobbyDtoIn hobbyDtoIn);

}
