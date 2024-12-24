package org.kafka.practice.kafkademo.domain.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FillRandomAllDataDtoIn {

    private final int personsCount;
    private final int personHobbiesCount;
    private final int initCompaniesCount;
    private final int initHobbiesCount;

}
