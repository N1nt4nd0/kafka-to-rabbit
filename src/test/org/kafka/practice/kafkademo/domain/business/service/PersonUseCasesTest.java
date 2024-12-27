package org.kafka.practice.kafkademo.domain.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.person.CompanyManagementType;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.CompanyManagementException;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyHasHobbyException;
import org.kafka.practice.kafkademo.domain.exception.PersonHaveNoHobbyException;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonUseCasesTest {

    @InjectMocks
    private PersonUseCasesImpl sut;

    @Mock
    private CompanyService companyService;

    @Mock
    private PersonService personService;

    @Mock
    private HobbyService hobbyService;

    @Test
    void testHirePersonAtCompanySuccessfully() {
        final var expectedMessage = "Person was hired successfully";

        final var company = Mockito.mock(Company.class);
        final var person = Mockito.mock(Person.class);

        Mockito.when(companyService.getByCompanyName(Mockito.anyString())).thenReturn(company);
        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(personService.savePerson(Mockito.any())).thenReturn(person);
        Mockito.when(person.isCompanyEmployee(company)).thenReturn(false);
        Mockito.when(company.getCompanyName()).thenReturn("Company");
        Mockito.when(person.getEmail()).thenReturn("email@email");

        final var resultingResponse = sut.manageCompany(new CompanyManagementDtoIn("Person", "Company",
                CompanyManagementType.HIRE));

        Assertions.assertEquals(expectedMessage, resultingResponse.getMessage());
    }

    @Test
    void testDismissPersonFromCompanySuccessfully() {
        final var expectedMessage = "Person was dismissed successfully";

        final var company = Mockito.mock(Company.class);
        final var person = Mockito.mock(Person.class);

        Mockito.when(companyService.getByCompanyName(Mockito.anyString())).thenReturn(company);
        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(personService.savePerson(Mockito.any())).thenReturn(person);
        Mockito.when(person.isCompanyEmployee(company)).thenReturn(true);
        Mockito.when(company.getCompanyName()).thenReturn("Company");
        Mockito.when(person.getEmail()).thenReturn("email@email");

        final var resultingResponse = sut.manageCompany(new CompanyManagementDtoIn("Person", "Company",
                CompanyManagementType.DISMISS));

        Assertions.assertEquals(expectedMessage, resultingResponse.getMessage());
    }

    @Test
    void testAddHobbyToPersonSuccessfully() {
        final var expectedMessage = "Hobby added successfully";

        final var person = Mockito.mock(Person.class);
        final var hobby = Mockito.mock(Hobby.class);

        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(hobbyService.getByHobbyName(Mockito.anyString())).thenReturn(hobby);
        Mockito.when(person.hasHobby(hobby)).thenReturn(false);
        Mockito.when(person.withAddedHobby(hobby)).thenReturn(person);

        final var resultingResponse = sut.addHobby(new AddPersonHobbyDtoIn("test@test.com", "Hobby"));

        Assertions.assertEquals(expectedMessage, resultingResponse.getMessage());
    }

    @Test
    void testRemovePersonHobbySuccessfully() {
        final var expectedMessage = "Hobby removed successfully";

        final var person = Mockito.mock(Person.class);
        final var hobby = Mockito.mock(Hobby.class);

        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(hobbyService.getByHobbyName(Mockito.anyString())).thenReturn(hobby);
        Mockito.when(person.hasHobby(hobby)).thenReturn(true);
        Mockito.when(person.withRemovedHobby(hobby)).thenReturn(person);

        final var resultingResponse = sut.removeHobby(new RemovePersonHobbyDtoIn("test@test.com", "Hobby"));

        Assertions.assertEquals(expectedMessage, resultingResponse.getMessage());
    }

    @Test
    void testManagePersonCompanyThrowCompanyManagementExceptionWhenPersonAlreadyHired() {
        final var expectedMessage = "Person already hired at company";

        final var company = Mockito.mock(Company.class);
        final var person = Mockito.mock(Person.class);

        Mockito.when(person.isCompanyEmployee(company)).thenReturn(true);
        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(companyService.getByCompanyName(Mockito.anyString())).thenReturn(company);

        final var resultingException = Assertions.assertThrows(CompanyManagementException.class, () ->
                sut.manageCompany(new CompanyManagementDtoIn("Person", "Company", CompanyManagementType.HIRE)));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testManagePersonCompanyThrowCompanyManagementExceptionWhenPersonNotHired() {
        final var expectedMessage = "Person is not company employee";

        final var company = Mockito.mock(Company.class);
        final var person = Mockito.mock(Person.class);

        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(companyService.getByCompanyName(Mockito.anyString())).thenReturn(company);
        Mockito.when(person.isCompanyEmployee(company)).thenReturn(false);

        final var resultingException = Assertions.assertThrows(CompanyManagementException.class, () ->
                sut.manageCompany(new CompanyManagementDtoIn("Person", "Company", CompanyManagementType.DISMISS)));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testAddHobbyToPersonThrowPersonAlreadyHasHobbyExceptionWhenPersonHaveSpecifiedHobby() {
        final var expectedMessagePrefix = "Person already has hobby";

        final var person = Mockito.mock(Person.class);
        final var hobby = Mockito.mock(Hobby.class);

        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(hobbyService.getByHobbyName(Mockito.anyString())).thenReturn(hobby);
        Mockito.when(person.hasHobby(hobby)).thenReturn(true);

        final var resultingException = Assertions.assertThrows(PersonAlreadyHasHobbyException.class, () ->
                sut.addHobby(new AddPersonHobbyDtoIn("test@test.com", "Hobby")));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

    @Test
    void testRemoveHobbyFromPersonThrowPersonHaveNoHobbyExceptionWhenPersonHaveNoSpecifiedHobby() {
        final var expectedMessagePrefix = "Person has no hobbies found";

        final var person = Mockito.mock(Person.class);
        final var hobby = Mockito.mock(Hobby.class);

        Mockito.when(personService.getByEmail(Mockito.anyString())).thenReturn(person);
        Mockito.when(hobbyService.getByHobbyName(Mockito.anyString())).thenReturn(hobby);
        Mockito.when(person.hasHobby(hobby)).thenReturn(false);

        final var resultingException = Assertions.assertThrows(PersonHaveNoHobbyException.class, () ->
                sut.removeHobby(new RemovePersonHobbyDtoIn("test@test.com", "Hobby")));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

}