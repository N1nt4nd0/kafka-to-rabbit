package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HobbyService {

    Page<Hobby> getHobbies(Pageable pageable);

}
