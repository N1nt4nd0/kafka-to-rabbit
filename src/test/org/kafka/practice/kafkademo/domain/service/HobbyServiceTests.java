package org.kafka.practice.kafkademo.domain.service;

import net.datafaker.Faker;
import net.datafaker.providers.base.Hobby;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.exception.HobbyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HobbyServiceTests {

    @InjectMocks
    private HobbyServiceImpl sut;

    @Mock
    private HobbyRepository hobbyRepository;

    @Mock
    private Faker dataFaker;

    @Test
    void testSuccessfullyGenerateTenRandomHobbiesWhenInputHasTwentyActivityNames() {
        final var fakerHobbyMock = Mockito.mock(Hobby.class);

        Mockito.when(dataFaker.hobby()).thenReturn(fakerHobbyMock);
        Mockito.when(fakerHobbyMock.activity()).thenReturn(
                "Activity01", "Activity02", "Activity03", "Activity04", "Activity05",
                "Activity06", "Activity07", "Activity08", "Activity09", "Activity10",
                "Activity11", "Activity12", "Activity13", "Activity14", "Activity15",
                "Activity16", "Activity17", "Activity18", "Activity19", "Activity20");

        final var expectedTen = sut.generateNRandomHobbies(10);

        Assertions.assertEquals(10, expectedTen);
    }

    @Test
    void testValidateGenerationCountThrowFillRandomExceptionWhenHobbyRepositoryAlreadyFilled() {
        final var expectedMessage = "Hobby database already filled";

        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(50));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomExceptionWhenRequestedCountLessThanTen() {
        final var expectedMessage = "Hobbies count must be greater than 10";

        Mockito.when(hobbyRepository.count()).thenReturn(0L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(10));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testGetByHobbyNameThrowHobbyNotFoundByNameExceptionWhenRepositoryHaveNoSpecifiedHobby() {
        final var expectedMessagePrefix = "Can't find hobby by name";

        Mockito.when(hobbyRepository.findByHobbyName(Mockito.anyString())).thenReturn(Optional.empty());

        final var resultingException = Assertions.assertThrows(HobbyNotFoundByNameException.class, () ->
                sut.getByHobbyName("Hobby"));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

}