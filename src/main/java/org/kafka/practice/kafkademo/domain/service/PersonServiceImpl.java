package org.kafka.practice.kafkademo.domain.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.PersonNotFoundByEmailException;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person updateOrCreate(@NonNull final String email,
                                 @NonNull final String firstName, @NonNull final String lastName) {
        log.debug("Starting to create or update person at database");
        final var personByEmailOptional = personRepository.findByEmailIgnoreCase(email);
        Person personToProcess;
        if (personByEmailOptional.isPresent()) {
            final var personByEmail = personByEmailOptional.get();
            personToProcess = new Person(personByEmail.getId(), personByEmail.getEmail(), firstName, lastName);
            log.debug("Person already exist, try to update person from {} to {}", personByEmail, personToProcess);
        } else {
            personToProcess = new Person(email, firstName, lastName);
            log.debug("It's a new person, try to create: {}", personToProcess);
        }
        return savePerson(personToProcess);
    }

    @Override
    public Page<Person> getPersons(@NonNull final Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person getByEmail(@NonNull final String email) {
        return personRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new PersonNotFoundByEmailException(email));
    }

    @Override
    public Person savePerson(@NonNull final Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(@NonNull final Person person) {
        personRepository.delete(person);
    }

    @Override
    public void deleteByEmail(@NonNull final String email) {
        final var person = getByEmail(email);
        deletePerson(person);
    }

}
