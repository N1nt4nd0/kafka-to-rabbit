package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonService {

    Person updateOrCreate(String email, String firstName, String lastName);

    Optional<Person> getByEmail(String email);

    void deleteByEmail(String email);

    Page<Person> getPersons(Pageable pageable);

    Person savePerson(Person person);

    void deletePerson(Person person);

}
