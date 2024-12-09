package org.kafka.practice.kafkademo.domain.dev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.mappers.PersonDtoMapper;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Profile("full-cycle-dev")
@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class NikitaScheduler {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String kafkaReceiveTopicName;

    private final ObjectFactory<Person> randomPersonGenerator;
    private final PersonDtoMapper personMapper;

    @Scheduled(fixedDelayString = "#{@nikitaKafkaProducerDelayMs}")
    public void kafkaSendAsNikita() {
        final var randomPerson = randomPersonGenerator.getObject();
        final var request = personMapper.toPersonDtoRequest(randomPerson);
        kafkaTemplate.send(kafkaReceiveTopicName, request);
    }

}
