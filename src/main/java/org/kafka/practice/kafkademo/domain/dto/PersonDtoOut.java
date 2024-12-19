package org.kafka.practice.kafkademo.domain.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class PersonDtoOut {

    @NonNull
    private final UUID id;
    @NonNull
    private final String email;
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @NonNull
    private final String companyName;
    @NonNull
    private final List<HobbyDtoOut> hobbies;

}
