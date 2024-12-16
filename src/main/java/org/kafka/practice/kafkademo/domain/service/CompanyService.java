package org.kafka.practice.kafkademo.domain.service;

import org.kafka.practice.kafkademo.domain.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    Page<Company> getCompanies(Pageable pageable);

    Company getByCompanyName(String companyName);

    Company getRandomCompany(int bound);

    List<Company> fillRandomCompanies(int count);

    Company createNewCompany(String companyName);

    Company saveCompany(Company company);

    void deleteCompany(Company company);

    void deleteByCompanyName(String companyName);

}
