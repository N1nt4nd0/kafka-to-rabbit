package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.exception.CompanyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final int pageMaxElementsSize;

    @Override
    @Transactional
    public Page<Company> getCompanies(final Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Company getRandomCompany() {
        final var companyPage = getCompanies(PageRequest.of(0, pageMaxElementsSize));
        final var companyList = companyPage.getContent();
        final var randomCompanyIndex = new Random().nextInt(companyList.size());
        return companyList.get(randomCompanyIndex);
    }

    @Override
    @Transactional
    public Company getByCompanyName(final String companyName) {
        return companyRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new CompanyNotFoundByNameException(companyName));
    }

    @Override
    @Transactional
    public Company createNewCompany(final String companyName) {
        final var newCompany = Company.blankCompany(companyName);
        return saveCompany(newCompany);
    }

    @Override
    @Transactional
    public void deleteByCompanyName(final String companyName) {
        final var companyByName = getByCompanyName(companyName);
        deleteCompany(companyByName);
    }

    @Override
    @Transactional
    public Company saveCompany(final Company company) {
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public void deleteCompany(final Company company) {
        companyRepository.delete(company);
    }

    @Override
    @Transactional
    public long getCompanyCount() {
        return companyRepository.count();
    }

}
