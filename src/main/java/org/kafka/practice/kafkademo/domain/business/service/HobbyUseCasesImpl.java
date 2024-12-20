package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.HobbyCountDtoOut;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.mappers.HobbyMapper;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyUseCasesImpl implements HobbyUseCases {

    private final HobbyService hobbyService;
    private final HobbyMapper hobbyMapper;
    private final Faker dataFaker;

    @Override
    @Transactional
    public FillRandomDataDtoOut fillRandomHobbies(FillRandomHobbiesDtoIn fillRandomHobbiesDtoIn) {
        if (hobbyService.getHobbyCount() > 0) {
            throw new FillRandomDataException("Hobbies already filled");
        }
        Stream.generate(() -> dataFaker.hobby().activity())
                .distinct()
                .limit(fillRandomHobbiesDtoIn.getHobbiesCount())
                .forEach(hobbyService::createNewHobby);
        return new FillRandomDataDtoOut("Random hobbies successfully filled", hobbyService.getHobbyCount());
    }

    @Override
    @Transactional
    public Page<HobbyDtoOut> getHobbies(final Pageable pageable) {
        return hobbyService.getHobbies(pageable).map(hobbyMapper::toHobbyDtoOut);
    }

    @Override
    @Transactional
    public HobbyCountDtoOut getHobbyCount() {
        return new HobbyCountDtoOut(hobbyService.getHobbyCount());
    }

}
