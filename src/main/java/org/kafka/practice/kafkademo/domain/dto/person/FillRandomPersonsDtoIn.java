package org.kafka.practice.kafkademo.domain.dto.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FillRandomPersonsDtoIn {

    private final int personCount;
    private final int hobbyMaxCount;

}
