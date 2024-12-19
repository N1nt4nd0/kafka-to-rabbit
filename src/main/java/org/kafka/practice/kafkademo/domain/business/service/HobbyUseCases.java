package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyUseCases {

    Page<HobbyDtoOut> getHobbies(Pageable pageable);

    FillRandomDataDtoOut fillRandomHobbies(FillRandomHobbiesDtoIn fillRandomHobbiesDtoIn);

}
