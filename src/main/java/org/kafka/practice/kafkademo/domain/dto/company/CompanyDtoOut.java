package org.kafka.practice.kafkademo.domain.dto.company;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyDtoOut implements Serializable {

    @NonNull
    private final String id;
    @NonNull
    private final String companyName;

}
