package org.kafka.practice.kafkademo.domain.generation;

import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Job;
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
    public Job fakeRandomJob() {
        final var faker = new Faker();
        final var job = faker.job();
        return new Job(job.title());
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
    public ObjectFactory<Job> randomJobGenerator() {
        return this::fakeRandomJob;
    }

    @Bean
    public ObjectFactory<Hobby> randomHobbyGenerator() {
        return this::fakeRandomHobby;
    }

}