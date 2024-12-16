package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.JobDtoIn;
import org.kafka.practice.kafkademo.domain.dto.JobDtoOut;
import org.kafka.practice.kafkademo.domain.dto.job.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.job.EmployeeManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.job.FillRandomJobsDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobBusinessService {

    EmployeeManagementDtoOut manageEmployee(EmployeeManagementDtoIn employeeManagementDtoIn);

    List<JobDtoOut> fillRandomJobs(FillRandomJobsDtoIn fillRandomJobsDtoIn);

    Page<JobDtoOut> getJobs(Pageable pageable);

    JobDtoOut getByTitle(String title);

    JobDtoOut createJob(JobDtoIn jobDtoIn);

    void deleteJob(JobDtoIn jobDtoIn);

}
