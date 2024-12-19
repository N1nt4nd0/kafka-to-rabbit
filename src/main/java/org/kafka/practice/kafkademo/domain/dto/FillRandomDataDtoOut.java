package org.kafka.practice.kafkademo.domain.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class FillRandomDataDtoOut {

    @NonNull
    private final String message;
    private final long filledCount;

}
