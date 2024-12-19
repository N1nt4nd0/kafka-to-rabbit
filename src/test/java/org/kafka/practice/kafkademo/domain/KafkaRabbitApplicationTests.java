package org.kafka.practice.kafkademo.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.business.company.EmployeeManagementType;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class KafkaRabbitApplicationTests {

    @Autowired
    CompanyUseCases companyUseCases;

    @Autowired
    PersonUseCases personUseCases;

    @Autowired
    PersonRepository personRepository;

    @Test
    void commonTests() {
        personUseCases.fillRandomPersons(new FillRandomPersonsDtoIn(10, 5));
//        final var result = personUseCases.removeHobby(new RemovePersonHobbyDtoIn("josette.goodwin@hotmail.com", 9, "Drawing"));
//        log.info("Result: {}", result);
//        final var result = companyUseCases.manageEmployee(new EmployeeManagementDtoIn("song.lubowitz@gmail.com", "Grady, O'Conner and King", EmployeeManagementType.HIRE));
//        log.info("Result: {}", result);
    }

}