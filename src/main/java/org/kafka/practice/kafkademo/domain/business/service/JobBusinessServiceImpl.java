package org.kafka.practice.kafkademo.domain.business.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.JobDtoIn;
import org.kafka.practice.kafkademo.domain.dto.JobDtoOut;
import org.kafka.practice.kafkademo.domain.dto.job.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.job.EmployeeManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.job.FillRandomJobsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.mappers.JobMapper;
import org.kafka.practice.kafkademo.domain.service.JobService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobBusinessServiceImpl implements JobBusinessService {

    private final PersonService personService;
    private final JobService jobService;
    private final JobMapper jobMapper;

    @Override
    public EmployeeManagementDtoOut manageEmployee(@NonNull final EmployeeManagementDtoIn employeeManagementDtoIn) {
        final var person = personService.getByEmail(employeeManagementDtoIn.getPersonEmail());
        final var job = jobService.getByTitle(employeeManagementDtoIn.getJobTitle());
        String message;
        if (employeeManagementDtoIn.getHireType() == EmployeeManagementDtoIn.HireType.HIRE) {
            job.hireEmployee(person);
            message = "Employee was hired successfully";
        } else {
            job.fireEmployee(person);
            message = "Employee was fired successfully";
        }
        final var savedPerson = personService.savePerson(person);
        return new EmployeeManagementDtoOut(savedPerson.getEmail(), job.getJobTitle(), message);
    }

    @Override
    public List<JobDtoOut> fillRandomJobs(@NonNull final FillRandomJobsDtoIn fillRandomJobsDtoIn) {
        return jobService.fillRandomJobs(fillRandomJobsDtoIn.getJobsCount()).stream()
                .map(jobMapper::toJobDtoOut).toList();
    }

    @Override
    public Page<JobDtoOut> getJobs(@NonNull final Pageable pageable) {
        return jobService.getJobs(pageable).map(jobMapper::toJobDtoOut);
    }

    @Override
    public JobDtoOut getByTitle(@NonNull final String title) {
        final var job = jobService.getByTitle(title);
        return jobMapper.toJobDtoOut(job);
    }

    @Override
    public JobDtoOut createJob(@NonNull final JobDtoIn jobDtoIn) {
        final var job = jobService.createNewJob(jobDtoIn.getJobTitle());
        return jobMapper.toJobDtoOut(job);
    }

    @Override
    public void deleteJob(@NonNull final JobDtoIn jobDtoIn) {
        jobService.deleteByJobTitle(jobDtoIn.getJobTitle());
    }

}
