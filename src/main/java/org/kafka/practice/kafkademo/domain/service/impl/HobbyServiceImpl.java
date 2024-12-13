package org.kafka.practice.kafkademo.domain.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepository hobbyRepository;

    @Override
    public Optional<Hobby> getByName(@NonNull final String hobbyName) {
        return hobbyRepository.findByHobbyName(hobbyName);
    }

    @Override
    public Hobby saveHobby(@NonNull final Hobby hobby) {
        return hobbyRepository.save(hobby);
    }

    @Override
    public void deleteHobby(@NonNull final Hobby hobby) {
        hobbyRepository.delete(hobby);
    }

}
