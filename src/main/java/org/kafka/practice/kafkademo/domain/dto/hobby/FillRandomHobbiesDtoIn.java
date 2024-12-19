package org.kafka.practice.kafkademo.domain.dto.hobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FillRandomHobbiesDtoIn {

    private final int hobbiesCount;

}
