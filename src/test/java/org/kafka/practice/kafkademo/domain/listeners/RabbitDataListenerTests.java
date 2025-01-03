package org.kafka.practice.kafkademo.domain.listeners;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.PersonDtoRedirectService;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTOResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RabbitDataListenerTests {

    @InjectMocks
    private RabbitDataListener sut;

    @Mock
    private PersonDtoRedirectService personDtoRedirectService;

    @Test
    void testRabbitPersonDtoResponseListenerReceiveSuccessfully() {
        final var personDTOResponse = Mockito.mock(PersonDTOResponse.class);

        sut.rabbitPersonDtoResponseListener(personDTOResponse);

        Mockito.verify(personDtoRedirectService).receivePersonDtoResponseFromRabbit(personDTOResponse);
    }

}
