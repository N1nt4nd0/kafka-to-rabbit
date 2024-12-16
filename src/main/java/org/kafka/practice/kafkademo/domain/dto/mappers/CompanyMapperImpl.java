package org.kafka.practice.kafkademo.domain.dto.mappers;

import lombok.NonNull;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDtoOut toCompanyDtoOut(@NonNull final Company company) {
        return new CompanyDtoOut(company.getId(), company.getCompanyName(),
                company.getEmployees().stream().map(Person::getEmail).toList());
    }

}
