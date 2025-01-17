package org.kafka.practice.kafkademo.domain.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class PersonDtoIn {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private final String email;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "Invalid first name length")
    private final String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Invalid last name length")
    private final String lastName;

}
