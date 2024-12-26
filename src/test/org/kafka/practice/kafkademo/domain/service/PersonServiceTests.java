package org.kafka.practice.kafkademo.domain.service;

import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyCompanyException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyHobbyException;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyExistException;
import org.kafka.practice.kafkademo.domain.exception.PersonNotFoundByEmailException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    @InjectMocks
    private PersonServiceImpl sut;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HobbyRepository hobbyRepository;

    @Mock
    private WebPagesConfig webPagesConfig;

    @Mock
    private Faker dataFaker;

    @Test
    void testValidateGenerationCountThrowNoAnyCompanyExceptionWhenCompanyRepositoryIsEmpty() {
        final var expectedMessage = "There is no any companies in database. Fill companies first";

        Mockito.when(companyRepository.count()).thenReturn(0L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(NoAnyCompanyException.class, () ->
                sut.validateGenerationCount(1, 0)).getMessage());
    }

    @Test
    void testValidateGenerationCountThrowNoAnyHobbyExceptionWhenHobbyRepositoryIsEmpty() {
        final var expectedMessage = "There is no any hobbies in database. Fill hobbies first";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(0L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(NoAnyHobbyException.class, () ->
                sut.validateGenerationCount(1, 0)).getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomDataExceptionWhenRequestedHobbyCountLessThanZero() {
        final var expectedMessage = "Persons hobbies count must be great than 0";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(1, -1)).getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomDataExceptionWhenRequestedPersonCountLessThanOne() {
        final var expectedMessage = "Persons count must be great than 1";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(0, 0)).getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomDataExceptionWhenRequestedPersonCountGreaterThanThousand() {
        final var expectedMessage = "Persons count must be less than 1000";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(1001, 0)).getMessage());
    }

    @Test
    void testGetByEmailThrowPersonNotFoundByEmailExceptionWhenRepositoryHaveNoSpecifiedPerson() {
        final var expectedMessagePrefix = "Can't find person by email";

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertTrue(Assertions.assertThrows(PersonNotFoundByEmailException.class, () ->
                sut.getByEmail("test@test.com")).getMessage().startsWith(expectedMessagePrefix));
    }

    @Test
    void testDeleteByEmailThrowPersonNotFoundByEmailExceptionWhenRepositoryHaveNoSpecifiedPerson() {
        final var expectedMessagePrefix = "Can't find person by email";

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertTrue(Assertions.assertThrows(PersonNotFoundByEmailException.class, () ->
                sut.deleteByEmail("test@test.com")).getMessage().startsWith(expectedMessagePrefix));
    }

    @Test
    void testCreateNewPersonThrowPersonAlreadyExistExceptionWhenCreatingPersonWithExistingEmail() {
        final var expectedMessagePrefix = "Person already exist with specified email";

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.of(Mockito.mock(Person.class)));

        Assertions.assertTrue(Assertions.assertThrows(PersonAlreadyExistException.class, () ->
                        sut.createPerson("test@test.com", "FirstName", "LastName")).getMessage()
                .startsWith(expectedMessagePrefix));
    }

    @Test
    void testGenerateNRandomPersonsWithGreaterThanZeroResult() {
        final var realFaker = new Faker();

        Mockito.when(dataFaker.internet()).thenReturn(realFaker.internet());
        Mockito.when(dataFaker.name()).thenReturn(realFaker.name());
        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);
        Mockito.when(companyRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(Mockito.mock(Company.class))));
        Mockito.when(hobbyRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(Mockito.mock(Hobby.class))));

        Assertions.assertTrue(sut.generateNRandomPersons(10, 0) > 0);
    }

}