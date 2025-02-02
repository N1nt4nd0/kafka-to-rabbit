package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.config.cache.CacheKeyBuilder;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.mappers.CompanyMapper;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyUseCasesImpl implements CompanyUseCases {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public FillRandomDataDtoOut fillRandomCompanies(final FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn) {
        companyService.validateGenerationCount(fillRandomCompaniesDtoIn.getCompanyCount());
        return new FillRandomDataDtoOut("Random companies successfully filled",
                companyService.generateNRandomCompanies(fillRandomCompaniesDtoIn.getCompanyCount()));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public CompanyDtoOut createCompany(final CompanyDtoIn companyDtoIn) {
        return companyMapper.toCompanyDtoOut(companyService.createNewCompany(companyDtoIn.getCompanyName()));
    }

    @Override
    @Transactional
    @Cacheable(value = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, key = "@cacheKeyBuilder.buildPageKey(#pageable)")
    public Page<CompanyDtoOut> getCompanies(final Pageable pageable) {
        return companyService.getCompanies(pageable).map(companyMapper::toCompanyDtoOut);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.COMPANY_PAGE_CACHE_NAME, allEntries = true)
    public TruncateTableDtoOut truncateCompanies() {
        companyService.truncateCompanyTable();
        return new TruncateTableDtoOut("Company table successfully truncated");
    }

}
