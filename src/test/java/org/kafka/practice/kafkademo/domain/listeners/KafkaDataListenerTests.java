package org.kafka.practice.kafkademo.domain.listeners;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.business.service.PersonDtoRedirectService;
import org.kafka.practice.kafkademo.domain.entities.value.PersonDTORequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaDataListenerTests {

    @InjectMocks
    private KafkaDataListener sut;

    @Mock
    private PersonDtoRedirectService personDtoRedirectService;

    @Test
    void testKafkaPersonDtoRequestListenerReceiveSuccessfully() {
        final var personDTORequest = Mockito.mock(PersonDTORequest.class);

        sut.kafkaPersonDtoRequestListener(personDTORequest);

        Mockito.verify(personDtoRedirectService).receivePersonDtoRequestFromKafka(personDTORequest);
    }

}
