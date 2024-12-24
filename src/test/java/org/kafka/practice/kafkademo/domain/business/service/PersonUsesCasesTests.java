package org.kafka.practice.kafkademo.domain.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.HobbyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyExistException;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
public class PersonUsesCasesTests {

    @MockitoBean
    private PersonRepository personRepository;

    @MockitoBean
    private HobbyRepository hobbyRepository;

    @Autowired
    private PersonUseCases personUseCases;

    @Test
    void testCreateAlreadyExistPerson() {
        final var email = "test@test.com";
        final var firstName = "John";
        final var lastName = "Doe";
        Mockito.when(personRepository.findByEmailIgnoreCase(email))
                .thenThrow(new PersonAlreadyExistException(email));
        Assertions.assertThrows(PersonAlreadyExistException.class, () ->
                personUseCases.createPerson(new PersonDtoIn(email, firstName, lastName)));
    }

    @Test
    void testAddNonExistentHobbyToPerson() {
        final var email = "test@test.com";
        final var hobbyName = "Hobby";
        final var person = Mockito.mock(Person.class);
        Mockito.when(personRepository.findByEmailIgnoreCase(email))
                .thenReturn(Optional.of(person));
        Mockito.when(hobbyRepository.findByHobbyName(hobbyName))
                .thenThrow(new HobbyNotFoundByNameException(hobbyName));
        Assertions.assertThrows(HobbyNotFoundByNameException.class, () ->
                personUseCases.addHobby(new AddPersonHobbyDtoIn(email, hobbyName)));
    }

}
