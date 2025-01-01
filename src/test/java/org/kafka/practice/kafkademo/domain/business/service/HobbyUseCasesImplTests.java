package org.kafka.practice.kafkademo.domain.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.mappers.HobbyMapper;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HobbyUseCasesImplTests {

    @InjectMocks
    private HobbyUseCasesImpl sut;

    @Mock
    private HobbyService hobbyService;

    @Mock
    private HobbyMapper hobbyMapper;

    @Test
    void testFillTenRandomHobbiesSuccessfully() {
        final var expectedMessage = "Random hobbies successfully filled";
        final var expectedCount = 10;

        Mockito.when(hobbyService.generateNRandomHobbies(expectedCount))
                .thenReturn((long) expectedCount);

        final var resultingResponse = sut.fillRandomHobbies(new FillRandomHobbiesDtoIn(expectedCount));

        Mockito.verify(hobbyService).validateGenerationCount(expectedCount);
        Mockito.verify(hobbyService).generateNRandomHobbies(expectedCount);

        Assertions.assertEquals(expectedMessage, resultingResponse.getMessage());
        Assertions.assertEquals(expectedCount, resultingResponse.getFilledCount());
    }

    @Test
    void testCreateHobbySuccessfully() {
        final var expectedHobby = "Hobby";

        Mockito.when(hobbyMapper.toHobbyDtoOut(Mockito.any())).thenReturn(Mockito.mock(HobbyDtoOut.class));

        sut.createHobby(new HobbyDtoIn(expectedHobby));

        Mockito.verify(hobbyService).createNewHobby(expectedHobby);
    }

    @Test
    void testGetHobbiesSuccessfully() {
        final var pageableMock = Mockito.mock(Pageable.class);

        Mockito.when(hobbyService.getHobbies(pageableMock)).thenReturn(new PageImpl<>(List.of()));

        sut.getHobbies(pageableMock);

        Mockito.verify(hobbyService).getHobbies(pageableMock);
    }

    @Test
    void testTruncateCompaniesSuccessfully() {
        sut.truncateHobbies();

        Mockito.verify(hobbyService).truncateHobbyTable();
    }

}
