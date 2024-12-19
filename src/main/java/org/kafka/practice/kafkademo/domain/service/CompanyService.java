package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    Page<Company> getCompanies(Pageable pageable);

    Company getByCompanyName(String companyName);

    Company createNewCompany(String companyName);

    void deleteByCompanyName(String companyName);

    Company saveCompany(Company company);

    void deleteCompany(Company company);

    long getCompanyCount();

}
