package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    long generateNRandomCompanies(int companyCount);

    void validateGenerationCount(int requestedCount);

    Page<Company> getCompanies(Pageable pageable);

    Company getByCompanyName(String companyName);

    Company createNewCompany(String companyName);

    Company saveCompany(Company company);

    void truncateCompanyTable();

    long getCompanyCount();

}
