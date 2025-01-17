package org.kafka.practice.kafkademo.domain.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;

public class AddPersonHobbyDtoInTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidateAddPersonHobbyDtoInWhenAllArgumentsAreValid() {
        final var violations = validator.validate(new AddPersonHobbyDtoIn("email@email", "Hobby"));

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void testValidateAddPersonHobbyDtoInWhenEmailHasInvalidFormat() {
        final var expectedMessage = "Invalid email format";

        final var violations = validator.validate(new AddPersonHobbyDtoIn("email@", "Hobby"));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidateAddPersonHobbyDtoInWhenHobbyNameIsEmpty() {
        final var expectedMessage = "Hobby name is required";

        final var violations = validator.validate(new AddPersonHobbyDtoIn("email@email", "     "));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidateAddPersonHobbyDtoInWhenHobbyNameSizeOutOfRange() {
        final var expectedMessage = "Invalid hobby name length";

        final var violations = validator.validate(new AddPersonHobbyDtoIn("email@email", "AB"));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

}
