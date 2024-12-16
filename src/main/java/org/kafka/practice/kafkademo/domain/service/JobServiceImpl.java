package org.kafka.practice.kafkademo.domain.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Job;
import org.kafka.practice.kafkademo.domain.exception.FillRandomJobsException;
import org.kafka.practice.kafkademo.domain.exception.GetRandomJobOutOfBoundsException;
import org.kafka.practice.kafkademo.domain.exception.JobNotFoundByTitleException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyJobException;
import org.kafka.practice.kafkademo.domain.repository.JobRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final ObjectFactory<Job> randomJobGenerator;
    private final JobRepository jobRepository;

    @Override
    public Page<Job> getJobs(@NonNull final Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job getByTitle(@NonNull final String title) {
        return jobRepository.findByJobTitle(title)
                .orElseThrow(() -> new JobNotFoundByTitleException(title));
    }

    @Override
    public Job getRandomJob(final int bound) {
        final var jobsCount = jobRepository.count();
        if (jobsCount == 0) {
            throw new NoAnyJobException();
        } else if (bound > jobsCount) {
            throw new GetRandomJobOutOfBoundsException(bound, jobsCount);
        }
        final int randomJobIndex = new Random().nextInt(bound);
        final var jobsPage = getJobs(PageRequest.of(0, bound));
        return jobsPage.getContent().get(randomJobIndex);
    }

    @Override
    public List<Job> fillRandomJobs(final int count) {
        final var jobsCount = jobRepository.count();
        if (jobsCount > 0) {
            throw new FillRandomJobsException("Can execute only if job database is empty");
        }
        final var randomJobList = Stream.generate(randomJobGenerator::getObject)
                .distinct()
                .limit(count)
                .toList();
        return randomJobList.stream().peek(this::saveJob).toList();
    }

    @Override
    public Job createNewJob(@NonNull final String jobTitle) {
        final var job = new Job(jobTitle);
        return saveJob(job);
    }

    @Override
    public Job saveJob(@NonNull final Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(@NonNull final Job job) {
        jobRepository.delete(job);
    }

    @Override
    public void deleteByJobTitle(@NonNull final String jobTitle) {
        final var job = getByTitle(jobTitle);
        deleteJob(job);
    }

}
