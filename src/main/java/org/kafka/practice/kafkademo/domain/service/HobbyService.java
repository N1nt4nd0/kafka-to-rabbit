package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Hobby;

import java.util.Optional;

public interface HobbyService {

    Optional<Hobby> getByName(String hobbyName);

    Hobby saveHobby(Hobby hobby);

    void deleteHobby(Hobby hobby);

}
