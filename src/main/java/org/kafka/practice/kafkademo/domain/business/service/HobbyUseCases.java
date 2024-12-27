package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyUseCases {

    FillRandomDataDtoOut fillRandomHobbies(FillRandomHobbiesDtoIn fillRandomHobbiesDtoIn);

    HobbyDtoOut createHobby(HobbyDtoIn hobbyDtoIn);

    Page<HobbyDtoOut> getHobbies(Pageable pageable);

    TruncateTableDtoOut truncateHobbies();

}
