package org.kafka.practice.kafkademo.domain.repository;

import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends MongoRepository<Person, UUID> {

    Optional<Person> findByEmailIgnoreCase(String email);

}
