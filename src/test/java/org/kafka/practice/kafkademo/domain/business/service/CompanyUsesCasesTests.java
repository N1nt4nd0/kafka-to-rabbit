package org.kafka.practice.kafkademo.domain.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.business.company.EmployeeManagementType;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.EmployeeManagementException;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CompanyUsesCasesTests {

    @MockitoBean
    private CompanyRepository companyRepository;

    @MockitoBean
    private PersonRepository personRepository;

    @Autowired
    private CompanyUseCases companyUseCases;

    @Test
    void testFillRandomCompaniesWhenCompaniesAlreadyFilled() {
        Mockito.when(companyRepository.count()).thenReturn(1L);
        Assertions.assertThrows(FillRandomDataException.class, () ->
                companyUseCases.fillRandomCompanies(new FillRandomCompaniesDtoIn(50)));
    }

    @Test
    void testHireEmployeeWhenAlreadyHired() { // TODO use only mock objects!
        final var email = "test@test.com";
        final var firstName = "John";
        final var lastName = "Doe";
        final var companyName = "TestCompany";
        final var company = new Company(null, companyName);
        final var person = new Person(null, email, firstName, lastName, company, List.of());
        Mockito.when(companyRepository.findByCompanyName(companyName))
                .thenReturn(Optional.of(company));
        Mockito.when(personRepository.findByEmailIgnoreCase(email))
                .thenReturn(Optional.of(person));
        Assertions.assertThrows(EmployeeManagementException.class, () ->
                companyUseCases.manageEmployee(new EmployeeManagementDtoIn(email, companyName,
                        EmployeeManagementType.HIRE)));
    }

}
