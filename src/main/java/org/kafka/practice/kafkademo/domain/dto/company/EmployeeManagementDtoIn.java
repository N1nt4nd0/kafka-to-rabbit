package org.kafka.practice.kafkademo.domain.dto.company;

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
    private final String companyName;
    @NonNull
    private final String personEmail;
    @NonNull
    private final HireType hireType;

}
