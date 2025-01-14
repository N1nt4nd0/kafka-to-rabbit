package org.kafka.practice.kafkademo.domain.service;

import net.datafaker.Faker;
import net.datafaker.providers.base.Internet;
import net.datafaker.providers.base.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.config.web.WebPagesConfig;
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
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTests {

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
    void testSuccessfullyGenerateTenRandomPersonsWhenInputHasTwentyEmails() {
        final var fakerInternetMock = Mockito.mock(Internet.class);
        final var fakerNameMock = Mockito.mock(Name.class);

        Mockito.when(dataFaker.internet()).thenReturn(fakerInternetMock);
        Mockito.when(dataFaker.name()).thenReturn(fakerNameMock);
        Mockito.when(webPagesConfig.getPageMaxElementsSize()).thenReturn(10);
        Mockito.when(companyRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(Mockito.mock(Company.class))));
        Mockito.when(hobbyRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(Mockito.mock(Hobby.class))));
        Mockito.when(fakerNameMock.firstName()).thenReturn("FirstName");
        Mockito.when(fakerNameMock.lastName()).thenReturn("LastName");
        Mockito.when(fakerInternetMock.emailAddress()).thenReturn(
                "01@gmail.com", "02@gmail.com", "03@gmail.com", "04@gmail.com", "05@gmail.com",
                "06@gmail.com", "07@gmail.com", "08@gmail.com", "09@gmail.com", "10@gmail.com",
                "11@gmail.com", "12@gmail.com", "13@gmail.com", "14@gmail.com", "15@gmail.com",
                "16@gmail.com", "17@gmail.com", "18@gmail.com", "19@gmail.com", "20@gmail.com");

        final var expectedTen = sut.generateNRandomPersons(10, 0);

        Assertions.assertEquals(10, expectedTen);
    }

    @Test
    void testValidateGenerationCountPassSuccessfully() {
        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        Assertions.assertDoesNotThrow(() -> sut.validateGenerationCount(1, 1));
    }

    @Test
    void testFindByPersonEmailSuccessfullyReturnsSpecifiedPerson() {
        final var expectedPerson = Mockito.mock(Person.class);

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.of(expectedPerson));

        final var resultingPerson = sut.getByEmail("email@email");

        Assertions.assertEquals(expectedPerson, resultingPerson);
    }

    @Test
    void testGetPersonsSuccessfullyCalled() {
        final var pageableMock = Mockito.mock(Pageable.class);

        sut.getPersons(pageableMock);

        Mockito.verify(personRepository).findAll(pageableMock);
    }

    @Test
    void testTruncatePersonTableSuccessfullyCalled() {
        sut.truncatePersonsTable();

        Mockito.verify(personRepository).deleteAll();
    }

    @Test
    void testCreatePersonSuccessfullyDone() {
        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.empty());

        sut.createPerson("email@email", "FirstName", "LastName");

        Mockito.verify(personRepository).save(Mockito.any());
    }

    @Test
    void testValidateGenerationCountThrowNoAnyCompanyExceptionWhenCompanyRepositoryIsEmpty() {
        final var expectedMessage = "There is no any companies in database. Fill companies first";

        Mockito.when(companyRepository.count()).thenReturn(0L);

        final var resultingException = Assertions.assertThrows(NoAnyCompanyException.class, () ->
                sut.validateGenerationCount(1, 0));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testDeletePersonSuccessfullyCalled() {
        final var expectedPerson = Mockito.mock(Person.class);

        sut.deletePerson(expectedPerson);

        Mockito.verify(personRepository).delete(expectedPerson);
    }

    @Test
    void testDeletePersonByEmailSuccessfullyCalled() {
        final var expectedPerson = Mockito.mock(Person.class);

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.of(expectedPerson));

        sut.deleteByEmail("email@email");

        Mockito.verify(personRepository).delete(expectedPerson);
    }

    @Test
    void testGetPersonCountSuccessfullyCalled() {
        sut.getPersonCount();

        Mockito.verify(personRepository).count();
    }

    @Test
    void testValidateGenerationCountThrowNoAnyHobbyExceptionWhenHobbyRepositoryIsEmpty() {
        final var expectedMessage = "There is no any hobbies in database. Fill hobbies first";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(0L);

        final var resultingException = Assertions.assertThrows(NoAnyHobbyException.class, () ->
                sut.validateGenerationCount(1, 0));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomDataExceptionWhenRequestedHobbyCountLessThanZero() {
        final var expectedMessage = "Persons hobbies count must be great than 0";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(1, -1));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomDataExceptionWhenRequestedPersonCountLessThanOne() {
        final var expectedMessage = "Persons count must be great than 1";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(0, 0));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomDataExceptionWhenRequestedPersonCountGreaterThanThousand() {
        final var expectedMessage = "Persons count must be less than 1000";

        Mockito.when(companyRepository.count()).thenReturn(1L);
        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(1001, 0));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testGetByEmailThrowPersonNotFoundByEmailExceptionWhenRepositoryHaveNoSpecifiedPerson() {
        final var expectedMessagePrefix = "Can't find person by email";

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.empty());

        final var resultingException = Assertions.assertThrows(PersonNotFoundByEmailException.class, () ->
                sut.getByEmail("test@test.com"));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

    @Test
    void testDeleteByEmailThrowPersonNotFoundByEmailExceptionWhenRepositoryHaveNoSpecifiedPerson() {
        final var expectedMessagePrefix = "Can't find person by email";

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString())).thenReturn(Optional.empty());

        final var resultingException = Assertions.assertThrows(PersonNotFoundByEmailException.class, () ->
                sut.deleteByEmail("test@test.com"));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

    @Test
    void testCreateNewPersonThrowPersonAlreadyExistExceptionWhenCreatingPersonWithExistingEmail() {
        final var expectedMessagePrefix = "Person already exist with specified email";

        Mockito.when(personRepository.findByEmailIgnoreCase(Mockito.anyString()))
                .thenReturn(Optional.of(Mockito.mock(Person.class)));

        final var resultingException = Assertions.assertThrows(PersonAlreadyExistException.class, () ->
                sut.createPerson("test@test.com", "FirstName", "LastName"));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

}