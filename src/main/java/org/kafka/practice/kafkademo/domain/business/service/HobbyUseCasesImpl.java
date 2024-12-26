package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.mappers.HobbyMapper;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyUseCasesImpl implements HobbyUseCases {

    private final HobbyService hobbyService;
    private final HobbyMapper hobbyMapper;

    @Override
    @Transactional
    public FillRandomDataDtoOut fillRandomHobbies(final FillRandomHobbiesDtoIn fillRandomHobbiesDtoIn) {
        hobbyService.validateGenerationCount(fillRandomHobbiesDtoIn.getHobbyCount());
        return new FillRandomDataDtoOut("Random hobbies successfully filled",
                hobbyService.generateNRandomHobbies(fillRandomHobbiesDtoIn.getHobbyCount()));
    }

    @Override
    @Transactional
    public HobbyDtoOut createHobby(final HobbyDtoIn hobbyDtoIn) {
        return hobbyMapper.toHobbyDtoOut(hobbyService.createNewHobby(hobbyDtoIn.getHobbyName()));
    }

    @Override
    @Transactional
    public Page<HobbyDtoOut> getHobbies(final Pageable pageable) {
        return hobbyService.getHobbies(pageable).map(hobbyMapper::toHobbyDtoOut);
    }

    @Override
    @Transactional
    public TruncateTableDtoOut truncateHobbies() {
        hobbyService.truncateHobbyTable();
        return new TruncateTableDtoOut("Hobby table successfully truncated");
    }

}
