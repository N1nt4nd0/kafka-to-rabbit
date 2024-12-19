package org.kafka.practice.kafkademo.domain.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class PersonCountDtoOut {

    private final long personCount;

}
