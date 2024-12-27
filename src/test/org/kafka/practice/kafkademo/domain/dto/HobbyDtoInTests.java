package org.kafka.practice.kafkademo.domain.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoIn;

public class HobbyDtoInTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidateHobbyDtoInWhenHobbyNameIsValid() {
        final var violations = validator.validate(new HobbyDtoIn("Hobby"));

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void testValidateHobbyDtoInWhenHobbyNameIsEmpty() {
        final var expectedMessage = "Hobby name is required";

        final var violations = validator.validate(new HobbyDtoIn("     "));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

}
