package org.kafka.practice.kafkademo.domain.repository;

import org.bson.types.ObjectId;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<Person, ObjectId> {

    Optional<Person> findByEmailIgnoreCase(String email);

}
