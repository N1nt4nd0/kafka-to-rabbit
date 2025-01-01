package org.kafka.practice.kafkademo.domain.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.mappers.CompanyMapper;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CompanyUseCasesImplTests {

    @InjectMocks
    private CompanyUseCasesImpl sut;

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyMapper companyMapper;

    @Test
    void testFillTenRandomCompaniesSuccessfully() {
        final var expectedMessage = "Random companies successfully filled";
        final var expectedCount = 10;

        Mockito.when(companyService.generateNRandomCompanies(expectedCount))
                .thenReturn((long) expectedCount);

        final var resultingResponse = sut.fillRandomCompanies(new FillRandomCompaniesDtoIn(expectedCount));

        Mockito.verify(companyService).validateGenerationCount(expectedCount);
        Mockito.verify(companyService).generateNRandomCompanies(expectedCount);

        Assertions.assertEquals(expectedMessage, resultingResponse.getMessage());
        Assertions.assertEquals(expectedCount, resultingResponse.getFilledCount());
    }

    @Test
    void testCreateCompanySuccessfully() {
        final var expectedCompany = "Company";

        Mockito.when(companyMapper.toCompanyDtoOut(Mockito.any())).thenReturn(Mockito.mock(CompanyDtoOut.class));

        sut.createCompany(new CompanyDtoIn(expectedCompany));

        Mockito.verify(companyService).createNewCompany(expectedCompany);
    }

    @Test
    void testGetCompaniesSuccessfully() {
        final var pageableMock = Mockito.mock(Pageable.class);

        Mockito.when(companyService.getCompanies(pageableMock)).thenReturn(new PageImpl<>(List.of()));

        sut.getCompanies(pageableMock);

        Mockito.verify(companyService).getCompanies(pageableMock);
    }

    @Test
    void testTruncateCompaniesSuccessfully() {
        sut.truncateCompanies();

        Mockito.verify(companyService).truncateCompanyTable();
    }

}
