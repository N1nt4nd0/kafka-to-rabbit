package org.kafka.practice.kafkademo.domain.generation;

import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class EntityGenerator {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Person fakeRandomPerson() {
        final var faker = new Faker();
        final var email = faker.internet().emailAddress();
        final var name = faker.name();
        return new Person(email, name.firstName(), name.lastName());
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Company fakeRandomCompany() {
        final var faker = new Faker();
        final var company = faker.company();
        return new Company(company.name());
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Hobby fakeRandomHobby() {
        final var faker = new Faker();
        final var hobby = faker.hobby();
        return new Hobby(hobby.activity());
    }

    @Bean
    public ObjectFactory<Person> randomPersonGenerator() {
        return this::fakeRandomPerson;
    }

    @Bean
    public ObjectFactory<Company> randomCompanyGenerator() {
        return this::fakeRandomCompany;
    }

    @Bean
    public ObjectFactory<Hobby> randomHobbyGenerator() {
        return this::fakeRandomHobby;
    }

}