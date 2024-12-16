package org.kafka.practice.kafkademo.domain.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class JobDtoOut {

    @NonNull
    private final Long id;
    @NonNull
    private final String jobTitle;
    @NonNull
    private final List<String> employeesEmails;

}
