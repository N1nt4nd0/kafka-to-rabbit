package org.kafka.practice.kafkademo.domain.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;
import org.kafka.practice.kafkademo.domain.generation.ExceptionGenerator;
import org.kafka.practice.kafkademo.domain.mappers.message.PersonDTOMessageMapper;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class PersonDtoRedirectServiceImplTests {

    @InjectMocks
    private PersonDtoRedirectServiceImpl sut;

    @Mock
    private PersonService personService;

    @Mock
    private PersonDTOMessageMapper messageMapper;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ExceptionGenerator exceptionGenerator;

    @Test
    void testReceivePersonDtoRequestFromKafkaSuccessfully() {
        final var expectedEmail = "email@email";
        final var expectedFirstName = "FirstName";
        final var expectedLastName = "LastName";
        final var expectedPersonDtoRequest = new PersonDTORequest(expectedEmail, expectedFirstName, expectedLastName);

        sut.receivePersonDtoRequestFromKafka(expectedPersonDtoRequest);

        Mockito.verify(personService).createPerson(expectedEmail, expectedFirstName, expectedLastName);
        Mockito.verify(exceptionGenerator).generateRandomException();
        Mockito.verify(messageMapper).clonePersonDtoRequest(expectedPersonDtoRequest);
    }

    @Test
    void testRedirectPersonDtoRequestToRabbitSuccessfully() {
        final var expectedPersonDtoRequest = new PersonDTORequest("email@email", "FirstName", "LastName");

        sut.redirectPersonDtoRequestToRabbit(expectedPersonDtoRequest);

        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.any(), Mockito.any(),
                Mockito.eq(expectedPersonDtoRequest));
    }

    @Test
    void testReceivePersonDtoResponseFromRabbitSuccessfully() {
        final var expectedPersonDtoResponse = new PersonDTOResponse("email@email", "FirstName", "LastName", false);

        sut.receivePersonDtoResponseFromRabbit(expectedPersonDtoResponse);

        Mockito.verify(messageMapper).clonePersonDtoResponse(expectedPersonDtoResponse);
    }

    @Test
    void testSendPersonDtoResponseToKafkaSuccessfully() {
        final var expectedPersonDtoResponse = new PersonDTOResponse("email@email", "FirstName", "LastName", false);

        sut.sendPersonDtoResponseToKafka(expectedPersonDtoResponse);

        Mockito.verify(kafkaTemplate).send(Mockito.any(), Mockito.eq(expectedPersonDtoResponse));
    }

    @Test
    void testReceivePersonDtoResponseFromRabbitWhenResponseIsFailed() {
        final var expectedEmail = "email@email";
        final var expectedPersonDtoResponse = new PersonDTOResponse(expectedEmail, "FirstName", "LastName", false);
        expectedPersonDtoResponse.setFail(true);

        sut.receivePersonDtoResponseFromRabbit(expectedPersonDtoResponse);

        Mockito.verify(personService).deleteByEmail(expectedEmail);
    }

    @Test
    void testReceivePersonDtoRequestFromKafkaThrowRandomGeneratorException() {
        final var expectedMessage = "Random generator exception occurred";

        Mockito.doThrow(new RandomGeneratorException()).when(exceptionGenerator).generateRandomException();

        final var resultingException = Assertions.assertThrows(RandomGeneratorException.class, () ->
                sut.receivePersonDtoRequestFromKafka(new PersonDTORequest("email@email", "FirstName", "LastName")));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

}
