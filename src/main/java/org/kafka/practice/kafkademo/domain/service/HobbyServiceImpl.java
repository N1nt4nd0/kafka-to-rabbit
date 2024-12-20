package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.exception.HobbyNotFoundByNameException;
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
    public Hobby getByHobbyName(final String hobbyName) {
        return hobbyRepository.findByHobbyName(hobbyName)
                .orElseThrow(() -> new HobbyNotFoundByNameException(hobbyName));
    }

    @Override
    @Transactional
    public Hobby createNewHobby(final String hobbyName) {
        final var newHobby = Hobby.blankHobby(hobbyName);
        return saveHobby(newHobby);
    }

    @Override
    @Transactional
    public Hobby saveHobby(final Hobby hobby) {
        return hobbyRepository.save(hobby);
    }

    @Override
    @Transactional
    public void deleteHobby(final Hobby hobby) {
        hobbyRepository.delete(hobby);
    }

    @Override
    @Transactional
    public long getHobbyCount() {
        return hobbyRepository.count();
    }

}
