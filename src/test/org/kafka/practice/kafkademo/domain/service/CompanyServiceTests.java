package org.kafka.practice.kafkademo.domain.service;

import net.datafaker.Faker;
import net.datafaker.providers.base.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kafka.practice.kafkademo.domain.exception.CompanyNotFoundByNameException;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTests {

    @InjectMocks
    private CompanyServiceImpl sut;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private Faker dataFaker;

    @Test
    void testSuccessfullyGenerateTenRandomCompaniesWhenInputHasTwentyCompanyNames() {
        final var fakerCompanyMock = Mockito.mock(Company.class);

        Mockito.when(dataFaker.company()).thenReturn(fakerCompanyMock);
        Mockito.when(fakerCompanyMock.name()).thenReturn(
                "Company01", "Company02", "Company03", "Company04", "Company05",
                "Company06", "Company07", "Company08", "Company09", "Company10",
                "Company11", "Company12", "Company13", "Company14", "Company15",
                "Company16", "Company17", "Company18", "Company19", "Company20");

        final var expectedTen = sut.generateNRandomCompanies(10);

        Assertions.assertEquals(10, expectedTen);
    }

    @Test
    void testValidateGenerationCountThrowFillRandomExceptionWhenCompanyRepositoryAlreadyFilled() {
        final var expectedMessage = "Company database already filled";

        Mockito.when(companyRepository.count()).thenReturn(1L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(50));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomExceptionWhenRequestedCountLessThanTen() {
        final var expectedMessage = "Companies count must be great than 10";

        Mockito.when(companyRepository.count()).thenReturn(0L);

        final var resultingException = Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(10));

        Assertions.assertEquals(expectedMessage, resultingException.getMessage());
    }

    @Test
    void testThrowCompanyNotFoundByNameExceptionWhenRepositoryHaveNoSpecifiedCompany() {
        final var expectedMessagePrefix = "Can't find company by name";

        Mockito.when(companyRepository.findByCompanyName(Mockito.anyString())).thenReturn(Optional.empty());

        final var resultingException = Assertions.assertThrows(CompanyNotFoundByNameException.class, () ->
                sut.getByCompanyName("Company"));

        Assertions.assertTrue(resultingException.getMessage().startsWith(expectedMessagePrefix));
    }

}