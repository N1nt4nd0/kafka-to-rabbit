package org.kafka.practice.kafkademo.domain.dto.person;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyManagementDtoOut {

    @NonNull
    private final String companyName;
    @NonNull
    private final String personEmail;
    @NonNull
    private final String message;

}
