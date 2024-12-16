package org.kafka.practice.kafkademo.domain.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.exception.CompanyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.exception.FillRandomCompaniesException;
import org.kafka.practice.kafkademo.domain.exception.GetRandomCompanyOutOfBoundsException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyCompanyException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final ObjectFactory<Company> randomCompanyGenerator;
    private final CompanyRepository companyRepository;

    @Override
    public Page<Company> getCompanies(@NonNull final Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public Company getByCompanyName(@NonNull final String companyName) {
        return companyRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new CompanyNotFoundByNameException(companyName));
    }

    @Override
    public Company getRandomCompany(final int bound) {
        final var companiesCount = companyRepository.count();
        if (companiesCount == 0) {
            throw new NoAnyCompanyException();
        } else if (bound > companiesCount) {
            throw new GetRandomCompanyOutOfBoundsException(bound, companiesCount);
        }
        final int randomCompanyIndex = new Random().nextInt(bound);
        final var companiesPage = getCompanies(PageRequest.of(0, bound));
        return companiesPage.getContent().get(randomCompanyIndex);
    }

    @Override
    public List<Company> fillRandomCompanies(final int count) {
        final var companiesCount = companyRepository.count();
        if (companiesCount > 0) {
            throw new FillRandomCompaniesException("Can execute only if companies database is empty");
        }
        final var randomCompaniesList = Stream.generate(randomCompanyGenerator::getObject)
                .distinct()
                .limit(count)
                .toList();
        return randomCompaniesList.stream().peek(this::saveCompany).toList();
    }

    @Override
    public Company createNewCompany(@NonNull final String companyName) {
        final var company = new Company(companyName);
        return saveCompany(company);
    }

    @Override
    public Company saveCompany(@NonNull final Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(@NonNull final Company company) {
        companyRepository.delete(company);
    }

    @Override
    public void deleteByCompanyName(@NonNull final String companyName) {
        final var company = getByCompanyName(companyName);
        deleteCompany(company);
    }

}
