package org.kafka.practice.kafkademo.domain.dto.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyCountDtoOut {

    private final long companyCount;

}
