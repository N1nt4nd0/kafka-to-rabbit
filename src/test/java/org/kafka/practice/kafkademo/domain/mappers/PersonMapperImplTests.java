package org.kafka.practice.kafkademo.domain.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PersonMapperImplTests {

    @InjectMocks
    private PersonMapperImpl sut;

    @Mock
    private HobbyMapper hobbyMapper;

    @Test
    void testMapPersonWithJobAndHobbiesSuccessfully() {
        final var person = Mockito.mock(Person.class);
        final var personCompany = Mockito.mock(Company.class);
        final var personHobby = Mockito.mock(Hobby.class);
        final var personId = UUID.randomUUID();
        final var personEmail = "email@email.com";
        final var personFirstName = "FirstName";
        final var personLastName = "LastName";
        final var personCompanyName = "Company";

        Mockito.when(person.getId()).thenReturn(personId);
        Mockito.when(person.getEmail()).thenReturn(personEmail);
        Mockito.when(person.getFirstName()).thenReturn(personFirstName);
        Mockito.when(person.getLastName()).thenReturn(personLastName);
        Mockito.when(person.hasJob()).thenReturn(true);
        Mockito.when(person.getCompany()).thenReturn(personCompany);
        Mockito.when(personCompany.getCompanyName()).thenReturn(personCompanyName);
        Mockito.when(person.getHobbies()).thenReturn(List.of(personHobby));
        Mockito.when(hobbyMapper.toHobbyDtoOut(Mockito.any()))
                .thenReturn(Mockito.mock(HobbyDtoOut.class));

        final var resultingResponse = sut.toPersonDtoOut(person);

        Mockito.verify(person).getId();
        Mockito.verify(person).getEmail();
        Mockito.verify(person).getFirstName();
        Mockito.verify(person).getLastName();
        Mockito.verify(person).hasJob();
        Mockito.verify(person).getCompany();
        Mockito.verify(person).getHobbies();
        Mockito.verify(personCompany).getCompanyName();
        Mockito.verify(hobbyMapper).toHobbyDtoOut(Mockito.any());

        Assertions.assertEquals(personId, resultingResponse.getId());
        Assertions.assertEquals(personEmail, resultingResponse.getEmail());
        Assertions.assertEquals(personFirstName, resultingResponse.getFirstName());
        Assertions.assertEquals(personLastName, resultingResponse.getLastName());
        Assertions.assertEquals(personCompanyName, resultingResponse.getCompanyName());
    }

    @Test
    void testMapPersonWithoutJobAndHobbiesSuccessfully() {
        final var person = Mockito.mock(Person.class);
        final var personId = UUID.randomUUID();
        final var personEmail = "email@email.com";
        final var personFirstName = "FirstName";
        final var personLastName = "LastName";
        final var personCompanyName = "";

        Mockito.when(person.getId()).thenReturn(personId);
        Mockito.when(person.getEmail()).thenReturn(personEmail);
        Mockito.when(person.getFirstName()).thenReturn(personFirstName);
        Mockito.when(person.getLastName()).thenReturn(personLastName);
        Mockito.when(person.hasJob()).thenReturn(false);
        Mockito.when(person.getHobbies()).thenReturn(List.of());

        final var resultingResponse = sut.toPersonDtoOut(person);

        Mockito.verify(person).getId();
        Mockito.verify(person).getEmail();
        Mockito.verify(person).getFirstName();
        Mockito.verify(person).getLastName();
        Mockito.verify(person).hasJob();
        Mockito.verify(person).getHobbies();

        Assertions.assertEquals(personId, resultingResponse.getId());
        Assertions.assertEquals(personEmail, resultingResponse.getEmail());
        Assertions.assertEquals(personFirstName, resultingResponse.getFirstName());
        Assertions.assertEquals(personLastName, resultingResponse.getLastName());
        Assertions.assertEquals(personCompanyName, resultingResponse.getCompanyName());
    }

}