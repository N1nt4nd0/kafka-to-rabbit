package org.kafka.practice.kafkademo.domain.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CompanyDtoIn {

    @NotBlank(message = "Company name is required")
    private final String companyName;

}
