package org.kafka.practice.kafkademo.domain.mappers;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.mockito.Mockito;

public class HobbyMapperImplTests {

    private final HobbyMapper sut = new HobbyMapperImpl();

    @Test
    void testMapHobbySuccessfully() {
        final var hobby = Mockito.mock(Hobby.class);
        final var hobbyId = "64b7f4698a2b4e7e8b5732e4";
        final var hobbyName = "Hobby";

        Mockito.when(hobby.getId()).thenReturn(new ObjectId(hobbyId));
        Mockito.when(hobby.getHobbyName()).thenReturn(hobbyName);

        final var resultingResponse = sut.toHobbyDtoOut(hobby);

        Mockito.verify(hobby).getId();
        Mockito.verify(hobby).getHobbyName();

        Assertions.assertEquals(hobbyId, resultingResponse.getId());
        Assertions.assertEquals(hobbyName, resultingResponse.getHobbyName());
    }

}
