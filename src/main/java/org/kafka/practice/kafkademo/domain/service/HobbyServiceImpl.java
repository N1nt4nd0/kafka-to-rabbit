package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepository hobbyRepository;

    @Override
    @Transactional
    public Page<Hobby> getHobbies(final Pageable pageable) {
        return hobbyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteHobby(final Hobby hobby) {
        hobbyRepository.delete(hobby);
    }

}
