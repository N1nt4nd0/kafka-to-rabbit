package org.kafka.practice.kafkademo.domain.service;

import net.datafaker.Faker;
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
    void testValidateGenerationCountThrowFillRandomExceptionWhenCompanyRepositoryAlreadyFilled() {
        final var expectedMessage = "Company database already filled";

        Mockito.when(companyRepository.count()).thenReturn(1L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(50)).getMessage());
    }

    @Test
    void testValidateGenerationCountThrowFillRandomExceptionWhenRequestedCountLessThanTen() {
        final var expectedMessage = "Companies count must be great than 10";

        Mockito.when(companyRepository.count()).thenReturn(0L);

        Assertions.assertEquals(expectedMessage, Assertions.assertThrows(FillRandomDataException.class, () ->
                sut.validateGenerationCount(10)).getMessage());
    }

    @Test
    void testThrowCompanyNotFoundByNameExceptionWhenRepositoryHaveNoSpecifiedCompany() {
        Mockito.when(companyRepository.findByCompanyName(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(CompanyNotFoundByNameException.class, () -> sut.getByCompanyName("TestCompany"));
    }

    @Test
    void testGenerateNRandomCompaniesWithGreaterThanZeroResult() {
        final var realFaker = new Faker();

        Mockito.when(dataFaker.company()).thenReturn(realFaker.company());

        Assertions.assertTrue(sut.generateNRandomCompanies(10) > 0);
    }

}