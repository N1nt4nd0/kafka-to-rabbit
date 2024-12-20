package org.kafka.practice.kafkademo.domain.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FillRandomAllDataDtoOut {

    private final long filledPersons;
    private final long filledCompanies;
    private final long filledHobbies;

}
