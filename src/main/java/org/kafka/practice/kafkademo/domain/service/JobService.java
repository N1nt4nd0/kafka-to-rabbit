package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    Page<Job> getJobs(Pageable pageable);

    Job getByTitle(String title);

    Job getRandomJob(int bound);

    List<Job> fillRandomJobs(int count);

    Job createNewJob(String jobTitle);

    Job saveJob(Job job);

    void deleteJob(Job job);

    void deleteByJobTitle(String jobTitle);

}
