package org.kafka.practice.kafkademo.domain.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.kafka.practice.kafkademo.domain.business.person.CompanyManagementType;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyManagementDtoIn {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private final String personEmail;

    @NotBlank(message = "Company name is required")
    private final String companyName;

    @NonNull
    private final CompanyManagementType managementType;

}
