package org.kafka.practice.kafkademo.domain.repository;

import org.bson.types.ObjectId;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyRepository extends MongoRepository<Hobby, ObjectId> {

    Optional<Hobby> findByHobbyName(String hobbyName);

}
