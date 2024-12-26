package org.kafka.practice.kafkademo.domain.service;

import net.datafaker.Faker;
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
    void testValidateGenerationCountThrowFillRandomExceptionWhenHobbyRepositoryAlreadyFilled() {
        final var expectedMessage = "Hobby database already filled";

        Mockito.when(hobbyRepository.count()).thenReturn(1L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(50)).getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomExceptionWhenRequestedCountLessThanTen() {
        final var expectedMessage = "Hobbies count must be greater than 10";

        Mockito.when(hobbyRepository.count()).thenReturn(0L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(10)).getMessage());
    }

    @Test
    void testGetByHobbyNameThrowHobbyNotFoundByNameExceptionWhenRepositoryHaveNoSpecifiedHobby() {
        Mockito.when(hobbyRepository.findByHobbyName(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(HobbyNotFoundByNameException.class, () -> sut.getByHobbyName("TestHobby"));
    }

    @Test
    void testGenerateNRandomHobbiesWithGreaterThanZeroResult() {
        final var realFaker = new Faker();

        Mockito.when(dataFaker.hobby()).thenReturn(realFaker.hobby());
        
        Assertions.assertTrue(sut.generateNRandomHobbies(10) > 0);
    }

}