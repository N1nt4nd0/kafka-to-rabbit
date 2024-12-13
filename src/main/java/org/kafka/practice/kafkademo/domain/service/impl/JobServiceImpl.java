package org.kafka.practice.kafkademo.domain.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Job;
import org.kafka.practice.kafkademo.domain.repository.JobRepository;
import org.kafka.practice.kafkademo.domain.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public Optional<Job> getByTitle(@NonNull final String title) {
        return jobRepository.findByJobTitle(title);
    }

    @Override
    public Job saveJob(@NonNull final Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(@NonNull final Job job) {
        jobRepository.delete(job);
    }

}
