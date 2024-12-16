package org.kafka.practice.kafkademo.domain.dto.job;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class EmployeeManagementDtoOut {

    @NonNull
    private final String jobTitle;
    @NonNull
    private final String personEmail;
    @NonNull
    private final String message;

}
