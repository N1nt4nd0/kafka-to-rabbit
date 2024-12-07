package org.kafka.practice.kafkademo.domain.service.entities;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.entities.value.Person;
import org.kafka.practice.kafkademo.domain.repo.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public void savePerson(final Person person) {
        personRepository.save(person);
    }

    public void deletePerson(final Person person) {
        personRepository.delete(person);
    }

}
