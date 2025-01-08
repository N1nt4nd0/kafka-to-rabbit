package org.kafka.practice.kafkademo.domain.dto.company;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyDtoOut {

    @NonNull
    private final String id;
    @NonNull
    private final String companyName;

}
