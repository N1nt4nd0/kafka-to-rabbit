package org.kafka.practice.kafkademo.domain.dev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.generation.PersonMapper;
import org.kafka.practice.kafkademo.domain.entities.value.Person;
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
public class DevScheduleService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String receiveTopicName;

    private final ObjectFactory<Person> fakeRandomPerson;
    private final PersonMapper personMapper;

    @Scheduled(fixedDelay = 3000)
    public void kafkaSendAsNikita() {
        final var randomPerson = fakeRandomPerson.getObject();
        final var personDto = personMapper.toPersonDto(randomPerson);
        kafkaTemplate.send(receiveTopicName, personDto);
    }

}
