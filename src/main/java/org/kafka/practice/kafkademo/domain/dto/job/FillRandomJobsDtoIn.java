package org.kafka.practice.kafkademo.domain.dto.job;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FillRandomJobsDtoIn {

    private final int jobsCount;

}
