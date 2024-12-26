package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyUseCases {

    FillRandomDataDtoOut fillRandomCompanies(FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn);

    CompanyDtoOut createCompany(CompanyDtoIn companyDtoIn);

    Page<CompanyDtoOut> getCompanies(Pageable pageable);

    TruncateTableDtoOut truncateCompanies();

}
