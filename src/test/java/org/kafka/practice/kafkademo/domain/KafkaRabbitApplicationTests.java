package org.kafka.practice.kafkademo.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.business.service.HobbyUseCases;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class KafkaRabbitApplicationTests {

    @Autowired
    HobbyUseCases hobbyUseCases;

    @Autowired
    CompanyUseCases companyUseCases;

    @Autowired
    PersonUseCases personUseCases;

    @Test
    void commonTests() {
        personUseCases.fillRandomPersons(new FillRandomPersonsDtoIn(10, 5));
    }

}