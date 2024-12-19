package org.kafka.practice.kafkademo.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.kafka.practice.kafkademo.domain.service.TestService;
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

    @Autowired
    TestService testService;

    @Test
    @Transactional
    void commonTests() {
        testService.succTest();

    }

}