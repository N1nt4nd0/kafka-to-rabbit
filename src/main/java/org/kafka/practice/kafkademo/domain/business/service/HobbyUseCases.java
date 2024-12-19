package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyUseCases {

    Page<HobbyDtoOut> getHobbies(Pageable pageable);

}
