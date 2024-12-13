package org.kafka.practice.kafkademo.domain.repository;

import org.kafka.practice.kafkademo.domain.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findByJobTitle(String jobTitle);

}
