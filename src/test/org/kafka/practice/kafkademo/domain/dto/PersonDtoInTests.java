package org.kafka.practice.kafkademo.domain.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoIn;

public class PersonDtoInTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidatePersonDtoInWhenEmailHasInvalidFormat() {
        final var expectedMessage = "Invalid email format";

        final var violations = validator.validate(new PersonDtoIn("email@", "FirstName", "LastName"));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidatePersonDtoInWhenFirstNameIsEmpty() {
        final var expectedMessage = "First name is required";

        final var violations = validator.validate(new PersonDtoIn("email@email", "     ", "LastName"));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidatePersonDtoInWhenLastNameIsEmpty() {
        final var expectedMessage = "Last name is required";

        final var violations = validator.validate(new PersonDtoIn("email@email", "FirstName", "     "));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidatePersonDtoInWhenAllArgumentsAreValid() {
        final var violations = validator.validate(new PersonDtoIn("email@email", "FirstName", "LastName"));

        Assertions.assertEquals(0, violations.size());
    }

}
