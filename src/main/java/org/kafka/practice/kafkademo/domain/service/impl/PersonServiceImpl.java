package org.kafka.practice.kafkademo.domain.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Page<Person> getPersons(@NonNull final Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Person savePerson(@NonNull final Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(@NonNull final Person person) {
        personRepository.delete(person);
    }

}
