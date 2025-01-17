package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.config.cache.CacheKeyBuilder;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.CompanyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MongoTemplate mongoTemplate;
    private final Faker dataFaker;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public long generateNRandomCompanies(final int companyCount) {
        return Stream.generate(() -> dataFaker.company().name())
                .limit(companyCount)
                .distinct()
                .map(this::createNewCompany)
                .toList()
                .size();
    }

    @Override
    @Transactional
    public void validateGenerationCount(final int requestedCount) {
        if (getCompanyCount() > 0) {
            throw new FillRandomDataException("Company database already filled");
        }
        if (requestedCount <= 10) {
            throw new FillRandomDataException("Companies count must be great than 10");
        }
    }

    @Override
    @Transactional
    public Page<Company> getCompanies(final Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Company getByCompanyName(final String companyName) {
        return companyRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new CompanyNotFoundByNameException(companyName));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public Company createNewCompany(final String companyName) {
        return saveCompany(new Company(null, companyName));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public Company saveCompany(final Company company) {
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public void truncateCompanyTable() {
        companyRepository.deleteAll();
        mongoTemplate.updateMulti(new Query(), new Update().unset(Person.Fields.company), Person.class);
    }

    @Override
    @Transactional
    public long getCompanyCount() {
        return companyRepository.count();
    }

}
