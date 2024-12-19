package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.mappers.HobbyMapper;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HobbyUseCasesImpl implements HobbyUseCases {

    private final HobbyService hobbyService;
    private final HobbyMapper hobbyMapper;

    @Override
    public Page<HobbyDtoOut> getHobbies(final Pageable pageable) {
        return hobbyService.getHobbies(pageable).map(hobbyMapper::toHobbyDtoOut);
    }

}
