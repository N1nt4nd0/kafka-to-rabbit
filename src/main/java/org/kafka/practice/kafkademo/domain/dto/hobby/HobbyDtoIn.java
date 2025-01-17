package org.kafka.practice.kafkademo.domain.dto.hobby;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class HobbyDtoIn {

    @NotBlank(message = "Hobby name is required")
    @Size(min = 3, max = 50, message = "Invalid hobby name length")
    private final String hobbyName;

}
