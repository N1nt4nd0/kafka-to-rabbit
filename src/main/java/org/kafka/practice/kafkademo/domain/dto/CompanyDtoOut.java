package org.kafka.practice.kafkademo.domain.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyDtoOut {

    private final long id;
    @NonNull
    private final String companyName;

}
