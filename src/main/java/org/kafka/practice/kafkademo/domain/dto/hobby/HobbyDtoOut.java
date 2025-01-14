package org.kafka.practice.kafkademo.domain.dto.hobby;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
public class HobbyDtoOut implements Serializable {

    @NonNull
    private final String id;
    @NonNull
    private final String hobbyName;

}
