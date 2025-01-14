package org.kafka.practice.kafkademo.domain.dto.person;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;

import java.io.Serializable;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class PersonDtoOut implements Serializable {

    @NonNull
    private final String id;
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
