package org.kafka.practice.kafkademo.domain.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyDtoIn {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 50, message = "Invalid company name length")
    private final String companyName;

}
