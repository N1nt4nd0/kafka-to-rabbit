package org.kafka.practice.kafkademo.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class KafkaRabbitApplicationTests {

    @Autowired
    PersonService personService;

    @Test
    void commonTests() {
        final var person = new Person(null, "SICc@gmail.com", "SUCC", "RAR");
        personService.createPerson(person);


//        final var personsPage = personService.getPersons(Pageable.ofSize(100));
//        log.info("{}", personsPage.getContent());

//        final var personDtoRequest = new PersonDTORequest("SICC@gmail.com", "SUCC_rename", "SACC");
//        final var person = personService.updateOrCreatePerson(personDtoRequest);
//        log.info("Person: {}", person);

//        final var person = personService.getPersonByEmail("siCC@gmail.com");
//        log.info("Person: {}", person);
    }

}