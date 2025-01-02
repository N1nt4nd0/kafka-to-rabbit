package org.kafka.practice.kafkademo.domain.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.mockito.Mockito;

public class CompanyMapperImplTests {

    private final CompanyMapper sut = new CompanyMapperImpl();

    @Test
    void testMapCompanySuccessfully() {
        final var company = Mockito.mock(Company.class);
        final var companyId = 1L;
        final var companyName = "Company";

        Mockito.when(company.getId()).thenReturn(companyId);
        Mockito.when(company.getCompanyName()).thenReturn(companyName);

        final var resultingResponse = sut.toCompanyDtoOut(company);

        Mockito.verify(company).getId();
        Mockito.verify(company).getCompanyName();

        Assertions.assertEquals(companyId, resultingResponse.getId());
        Assertions.assertEquals(companyName, resultingResponse.getCompanyName());
    }

}
