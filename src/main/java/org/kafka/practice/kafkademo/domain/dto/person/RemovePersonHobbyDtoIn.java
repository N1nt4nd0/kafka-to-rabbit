package org.kafka.practice.kafkademo.domain.dto.person;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class RemovePersonHobbyDtoIn {

    @NonNull
    private final String email;
    @NonNull
    private final String hobbyName;

}
