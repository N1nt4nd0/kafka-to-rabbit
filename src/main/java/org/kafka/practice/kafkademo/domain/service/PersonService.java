package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    long generateNRandomPersons(int personCount, int personMaxHobbyCount);

    void validateGenerationCount(int requestedCount, int requestedHobbyCount);

    Person createPerson(String email, String firstName, String lastName);

    Page<Person> getPersons(Pageable pageable);

    Person getByEmail(String email);

    Person savePerson(Person person);

    void deletePerson(Person person);

    void deleteByEmail(String email);

    void truncatePersonsTable();

    long getPersonCount();

}
