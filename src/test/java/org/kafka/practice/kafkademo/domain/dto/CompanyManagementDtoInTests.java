package org.kafka.practice.kafkademo.domain.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.business.person.CompanyManagementType;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoIn;

public class CompanyManagementDtoInTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidateCompanyManagementDtoInWhenAllArgumentsAreValid() {
        final var violations = validator.validate(new CompanyManagementDtoIn("email@email", "Company",
                CompanyManagementType.HIRE));

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void testValidateCompanyManagementDtoInWhenEmailHasInvalidFormat() {
        final var expectedMessage = "Invalid email format";

        final var violations = validator.validate(new CompanyManagementDtoIn("email@", "Company",
                CompanyManagementType.HIRE));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidateCompanyManagementDtoInWhenCompanyNameIsEmpty() {
        final var expectedMessage = "Company name is required";

        final var violations = validator.validate(new CompanyManagementDtoIn("email@email", "     ",
                CompanyManagementType.HIRE));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    @Test
    void testValidateCompanyManagementDtoInWhenCompanyNameSizeOutOfRange() {
        final var expectedMessage = "Invalid company name length";

        final var violations = validator.validate(new CompanyManagementDtoIn("email@email", "A",
                CompanyManagementType.HIRE));

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

}
