package org.kafka.practice.kafkademo.domain.mappers;

import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Company;

public interface CompanyMapper {

    CompanyDtoOut toCompanyDtoOut(Company company);

}
