package org.kafka.practice.kafkademo.domain.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AddPersonHobbyDtoIn {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private final String email;

    @NotBlank(message = "Hobby name is required")
    private final String hobbyName;

}
