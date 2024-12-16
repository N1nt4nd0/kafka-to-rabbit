package org.kafka.practice.kafkademo.domain.dto.mappers;

import lombok.NonNull;
import org.kafka.practice.kafkademo.domain.dto.JobDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Job;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public JobDtoOut toJobDtoOut(@NonNull final Job job) {
        return new JobDtoOut(job.getId(), job.getJobTitle(),
                job.getEmployees().stream().map(Person::getEmail).toList());
    }

}
