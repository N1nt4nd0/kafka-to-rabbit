package org.kafka.practice.kafkademo.domain.mappers.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.mockito.Mockito;

public class PersonDTOMessageMapperImplTests {

    private final PersonDTOMessageMapper sut = new PersonDTOMessageMapperImpl();

    @Test
    void testMapPersonDtoRequestToPersonDtoResponseSuccessfully() {
        final var personDTORequest = Mockito.mock(PersonDTORequest.class);
        final var personEmail = "email@email.com";
        final var personFirstName = "FirstName";
        final var personLastName = "LastName";

        Mockito.when(personDTORequest.getEmail()).thenReturn(personEmail);
        Mockito.when(personDTORequest.getFirstName()).thenReturn(personFirstName);
        Mockito.when(personDTORequest.getLastName()).thenReturn(personLastName);

        final var resultingResponse = sut.personDtoRequestToPersonDtoResponse(personDTORequest);

        Mockito.verify(personDTORequest).getEmail();
        Mockito.verify(personDTORequest).getFirstName();
        Mockito.verify(personDTORequest).getLastName();

        Assertions.assertEquals(personEmail, resultingResponse.getEmail());
        Assertions.assertEquals(personFirstName, resultingResponse.getFirstName());
        Assertions.assertEquals(personLastName, resultingResponse.getLastName());
    }

    @Test
    void testClonePersonDtoRequestSuccessfully() {
        final var personDTORequest = Mockito.mock(PersonDTORequest.class);
        final var personEmail = "email@email.com";
        final var personFirstName = "FirstName";
        final var personLastName = "LastName";

        Mockito.when(personDTORequest.getEmail()).thenReturn(personEmail);
        Mockito.when(personDTORequest.getFirstName()).thenReturn(personFirstName);
        Mockito.when(personDTORequest.getLastName()).thenReturn(personLastName);

        final var resultingResponse = sut.clonePersonDtoRequest(personDTORequest);

        Mockito.verify(personDTORequest).getEmail();
        Mockito.verify(personDTORequest).getFirstName();
        Mockito.verify(personDTORequest).getLastName();

        Assertions.assertEquals(personEmail, resultingResponse.getEmail());
        Assertions.assertEquals(personFirstName, resultingResponse.getFirstName());
        Assertions.assertEquals(personLastName, resultingResponse.getLastName());
    }

    @Test
    void testClonePersonDtoResponseWithFailTrueSuccessfully() {
        final var personDTOResponse = Mockito.mock(PersonDTOResponse.class);
        final var personEmail = "email@email.com";
        final var personFirstName = "FirstName";
        final var personLastName = "LastName";
        final var fail = true;

        Mockito.when(personDTOResponse.getEmail()).thenReturn(personEmail);
        Mockito.when(personDTOResponse.getFirstName()).thenReturn(personFirstName);
        Mockito.when(personDTOResponse.getLastName()).thenReturn(personLastName);
        Mockito.when(personDTOResponse.isFail()).thenReturn(fail);

        final var resultingResponse = sut.clonePersonDtoResponse(personDTOResponse);

        Mockito.verify(personDTOResponse).getEmail();
        Mockito.verify(personDTOResponse).getFirstName();
        Mockito.verify(personDTOResponse).getLastName();
        Mockito.verify(personDTOResponse).isFail();

        Assertions.assertEquals(personEmail, resultingResponse.getEmail());
        Assertions.assertEquals(personFirstName, resultingResponse.getFirstName());
        Assertions.assertEquals(personLastName, resultingResponse.getLastName());
        Assertions.assertEquals(fail, resultingResponse.isFail());
    }

}
