package org.kafka.practice.kafkademo.domain.entities.generation;

import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PersonGenerator {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person fakeRandomPerson() {
        final var faker = new Faker();
        final var email = faker.internet().emailAddress();
        final var name = faker.name();
        return Person.builder()
                .id(UUID.randomUUID())
                .email(email)
                .firstName(name.firstName())
                .lastName(name.lastName())
                .build();
    }

    @Bean
    public ObjectFactory<Person> randomPersonGenerator() {
        return this::fakeRandomPerson;
    }

}