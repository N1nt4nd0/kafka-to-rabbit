package org.kafka.practice.kafkademo.domain.dto.hobby;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class HobbyDtoIn {

    @NotBlank(message = "Hobby name is required")
    private final String hobbyName;

}
