package org.kafka.practice.kafkademo.domain.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoIn;

public class CompanyDtoInTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidateCompanyDtoInWhenCompanyNameIsEmpty() {
        final var expectedMessage = "Company name is required";

        final var violations = validator.validate(new CompanyDtoIn("     "));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidateCompanyDtoInWhenCompanyNameIsValid() {
        final var violations = validator.validate(new CompanyDtoIn("Company"));

        Assertions.assertEquals(0, violations.size());
    }

}
