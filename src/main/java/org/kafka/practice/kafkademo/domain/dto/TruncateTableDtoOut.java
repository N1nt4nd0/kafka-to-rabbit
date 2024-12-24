package org.kafka.practice.kafkademo.domain.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class TruncateTableDtoOut {

    @NonNull
    private final String truncateMessage;

}
