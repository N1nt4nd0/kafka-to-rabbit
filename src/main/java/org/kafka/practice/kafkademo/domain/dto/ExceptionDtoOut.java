package org.kafka.practice.kafkademo.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ExceptionDtoOut {

    private final String errorMessage;

}
