package org.kafka.practice.kafkademo.domain.dto.mappers;

import org.kafka.practice.kafkademo.domain.dto.JobDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Job;

public interface JobMapper {

    JobDtoOut toJobDtoOut(Job job);

}
