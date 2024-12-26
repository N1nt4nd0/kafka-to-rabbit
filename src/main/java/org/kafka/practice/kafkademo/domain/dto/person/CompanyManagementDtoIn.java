package org.kafka.practice.kafkademo.domain.dto.person;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.kafka.practice.kafkademo.domain.business.person.CompanyManagementType;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyManagementDtoIn {

    @NonNull
    private final String personEmail;
    @NonNull
    private final String companyName;
    @NonNull
    private final CompanyManagementType managementType;

}
