package org.kafka.practice.kafkademo.domain.mappers;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.mockito.Mockito;

public class CompanyMapperImplTests {

    private final CompanyMapper sut = new CompanyMapperImpl();

    @Test
    void testMapCompanySuccessfully() {
        final var company = Mockito.mock(Company.class);
        final var companyId = "64b7f4698a2b4e7e8b5732e4";
        final var companyName = "Company";

        Mockito.when(company.getId()).thenReturn(new ObjectId(companyId));
        Mockito.when(company.getCompanyName()).thenReturn(companyName);

        final var resultingResponse = sut.toCompanyDtoOut(company);

        Mockito.verify(company).getId();
        Mockito.verify(company).getCompanyName();

        Assertions.assertEquals(companyId, resultingResponse.getId());
        Assertions.assertEquals(companyName, resultingResponse.getCompanyName());
    }

}
