package org.kafka.practice.kafkademo.domain.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.mockito.Mockito;

public class HobbyMapperImplTests {

    private final HobbyMapper sut = new HobbyMapperImpl();

    @Test
    void testMapHobbySuccessfully() {
        final var hobby = Mockito.mock(Hobby.class);
        final var hobbyId = 1L;
        final var hobbyName = "Hobby";

        Mockito.when(hobby.getId()).thenReturn(hobbyId);
        Mockito.when(hobby.getHobbyName()).thenReturn(hobbyName);

        final var resultingResponse = sut.toHobbyDtoOut(hobby);

        Mockito.verify(hobby).getId();
        Mockito.verify(hobby).getHobbyName();

        Assertions.assertEquals(hobbyId, resultingResponse.getId());
        Assertions.assertEquals(hobbyName, resultingResponse.getHobbyName());
    }

}
