package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyService {

    long generateNRandomHobbies(int hobbyCount);

    void validateGenerationCount(int requestedCount);

    Page<Hobby> getHobbies(Pageable pageable);

    Hobby getByHobbyName(String hobbyName);

    Hobby createNewHobby(String hobbyName);

    Hobby saveHobby(Hobby hobby);

    void truncateHobbyTable();

    long getHobbyCount();
}
