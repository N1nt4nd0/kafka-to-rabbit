package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Job;

import java.util.Optional;

public interface JobService {

    Optional<Job> getByTitle(String title);

    Job saveJob(Job job);

    void deleteJob(Job job);

}
