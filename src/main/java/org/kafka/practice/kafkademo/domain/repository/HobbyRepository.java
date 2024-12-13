package org.kafka.practice.kafkademo.domain.repository;

import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {

    Optional<Hobby> findByHobbyName(String hobbyName);

}
