package org.kafka.practice.kafkademo.domain.dto.company;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.kafka.practice.kafkademo.domain.business.company.EmployeeManagementType;

@Getter
@ToString
@RequiredArgsConstructor
public class EmployeeManagementDtoIn {

    @NonNull
    private final String personEmail;
    @NonNull
    private final String companyName;
    @NonNull
    private final EmployeeManagementType managementType;

}
