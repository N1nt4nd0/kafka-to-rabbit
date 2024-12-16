package org.kafka.practice.kafkademo.domain.dto.mappers;

import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Company;

public interface CompanyMapper {

    CompanyDtoOut toCompanyDtoOut(Company company);

}
