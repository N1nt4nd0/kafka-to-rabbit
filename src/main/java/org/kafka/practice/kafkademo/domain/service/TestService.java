package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestService {

    private final PersonRepository personRepository;

    public void succTest() {
        final var optionalPerson = personRepository.findByEmailIgnoreCase("josette.goodwin@hotmail.com");
        if (optionalPerson.isPresent()) {
            final var person = optionalPerson.get();
            final var hobbies = person.getHobbies();
            person.getHobbies().clear();
            final var result = personRepository.save(person);
            log.info("Result: {}", result);
        }
    }

}
