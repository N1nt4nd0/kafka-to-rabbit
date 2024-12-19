package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyExistException;
import org.kafka.practice.kafkademo.domain.exception.PersonNotFoundByEmailException;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Person createPerson(final String email, final String firstName, final String lastName) {
        log.debug("Starting to create new person at database");
        final var personByEmailOptional = personRepository.findByEmailIgnoreCase(email);
        if (personByEmailOptional.isPresent()) {
            throw new PersonAlreadyExistException(email);
        }
        final var newPerson = Person.blankPerson(email, firstName, lastName);
        return savePerson(newPerson);
    }

    @Override
    @Transactional
    public Page<Person> getPersons(final Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Person getByEmail(final String email) {
        return personRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new PersonNotFoundByEmailException(email));
    }

    @Override
    @Transactional
    public Person savePerson(final Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void deletePerson(final Person person) {
        personRepository.delete(person);
    }

    @Override
    @Transactional
    public void deleteByEmail(final String email) {
        final var personByEmail = getByEmail(email);
        deletePerson(personByEmail);
    }

    @Override
    @Transactional
    public long getPersonCount() {
        return personRepository.count();
    }

}
