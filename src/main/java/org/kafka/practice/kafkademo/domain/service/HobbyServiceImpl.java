package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.exception.HobbyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepository hobbyRepository;
    private final Faker dataFaker;

    @Override
    @Transactional
    public long generateNRandomHobbies(int hobbyCount) {
        return Stream.generate(() -> dataFaker.hobby().activity())
                .limit(hobbyCount)
                .distinct()
                .map(this::createNewHobby)
                .toList()
                .size();
    }

    @Override
    @Transactional
    public void validateGenerationCount(int requestedCount) {
        if (getHobbyCount() > 0) {
            throw new FillRandomDataException("Hobby database already filled");
        }
        if (requestedCount <= 10) {
            throw new FillRandomDataException("Hobbies count must be greater than 10");
        }
    }

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
        return saveHobby(new Hobby(null, hobbyName));
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
    public void truncateHobbyTable() {
        hobbyRepository.deleteAll();
    }

    @Override
    @Transactional
    public long getHobbyCount() {
        return hobbyRepository.count();
    }

}
