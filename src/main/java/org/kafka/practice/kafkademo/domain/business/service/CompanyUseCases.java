package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyCountDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyUseCases {

    EmployeeManagementDtoOut manageEmployee(EmployeeManagementDtoIn employeeManagementDtoIn);

    FillRandomDataDtoOut fillRandomCompanies(FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn);

    Page<CompanyDtoOut> getCompanies(Pageable pageable);

    CompanyDtoOut getByCompanyName(String companyName);

    CompanyDtoOut createCompany(CompanyDtoIn companyDtoIn);

    void deleteCompany(CompanyDtoIn companyDtoIn);

    TruncateTableDtoOut truncateCompanies();

    CompanyCountDtoOut getCompanyCount();

}
