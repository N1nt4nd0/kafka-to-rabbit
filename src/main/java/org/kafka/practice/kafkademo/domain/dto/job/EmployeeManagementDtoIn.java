package org.kafka.practice.kafkademo.domain.dto.job;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class EmployeeManagementDtoIn {

    public enum HireType {

        HIRE, FIRE

    }

    @NonNull
    private final String jobTitle;
    @NonNull
    private final String personEmail;
    @NonNull
    private final HireType hireType;

}
