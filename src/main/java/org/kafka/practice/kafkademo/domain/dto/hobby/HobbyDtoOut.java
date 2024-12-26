package org.kafka.practice.kafkademo.domain.dto.hobby;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class HobbyDtoOut {

    @NonNull
    private final Long id;
    @NonNull
    private final String hobbyName;

}
