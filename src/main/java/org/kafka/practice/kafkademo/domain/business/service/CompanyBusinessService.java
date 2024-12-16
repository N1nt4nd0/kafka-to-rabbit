package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyBusinessService {

    EmployeeManagementDtoOut manageEmployee(EmployeeManagementDtoIn employeeManagementDtoIn);

    List<CompanyDtoOut> fillRandomCompanies(FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn);

    Page<CompanyDtoOut> getCompanies(Pageable pageable);

    CompanyDtoOut getByCompanyName(String companyName);

    CompanyDtoOut createCompany(CompanyDtoIn companyDtoIn);

    void deleteCompany(CompanyDtoIn companyDtoIn);

}
