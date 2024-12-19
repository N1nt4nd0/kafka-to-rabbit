package org.kafka.practice.kafkademo.domain.mappers;

import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDtoOut toCompanyDtoOut(final Company company) {
        return new CompanyDtoOut(company.getId(), company.getCompanyName());
    }

}
