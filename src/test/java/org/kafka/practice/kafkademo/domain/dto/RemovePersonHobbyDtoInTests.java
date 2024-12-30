package org.kafka.practice.kafkademo.domain.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;

public class RemovePersonHobbyDtoInTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidateRemovePersonHobbyDtoInWhenAllArgumentsAreValid() {
        final var violations = validator.validate(new RemovePersonHobbyDtoIn("email@email", "FirstName"));

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void testValidateRemovePersonHobbyDtoInWhenEmailIsInvalidFormat() {
        final var expectedMessage = "Invalid email format";

        final var violations = validator.validate(new RemovePersonHobbyDtoIn("email@", "Hobby"));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidateRemovePersonHobbyDtoInWhenHobbyNameIsEmpty() {
        final var expectedMessage = "Hobby name is required";

        final var violations = validator.validate(new RemovePersonHobbyDtoIn("email@email", "     "));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

}
