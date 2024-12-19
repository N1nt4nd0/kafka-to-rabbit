package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.business.company.EmployeeManagementType;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyCountDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.mappers.CompanyMapper;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.EmployeeManagementException;
import org.kafka.practice.kafkademo.domain.exception.FillRandomCompaniesException;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyUseCasesImpl implements CompanyUseCases {

    private final CompanyService companyService;
    private final PersonService personService;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public EmployeeManagementDtoOut manageEmployee(final EmployeeManagementDtoIn employeeManagementDtoIn) {
        final var personByEmail = personService.getByEmail(employeeManagementDtoIn.getPersonEmail());
        final var companyByName = companyService.getByCompanyName(employeeManagementDtoIn.getCompanyName());
        Person person;
        String message;
        if (employeeManagementDtoIn.getManagementType() == EmployeeManagementType.HIRE) {
            if (personByEmail.isCompanyEmployee(companyByName)) {
                throw new EmployeeManagementException("Person already hired at company");
            }
            person = personByEmail.withCompany(companyByName);
            message = "Employee was hired successfully";
        } else if (employeeManagementDtoIn.getManagementType() == EmployeeManagementType.DISMISS) {
            if (!personByEmail.isCompanyEmployee(companyByName)) {
                throw new EmployeeManagementException("Person is not company employee");
            }
            person = personByEmail.withoutCompany();
            message = "Employee was dismissed successfully";
        } else {
            throw new EmployeeManagementException("Unsupported management type");
        }
        final var savedPerson = personService.savePerson(person);
        return new EmployeeManagementDtoOut(savedPerson.getEmail(), companyByName.getCompanyName(), message);
    }

    @Override
    @Transactional
    public FillRandomDataDtoOut fillRandomCompanies(final FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn) {
        if (companyService.getCompanyCount() > 0) {
            throw new FillRandomCompaniesException("Companies already filled");
        }
        final var faker = new Faker();
        Stream.generate(() -> faker.company().name())
                .distinct()
                .limit(fillRandomCompaniesDtoIn.getCompaniesCount())
                .forEach(companyService::createNewCompany);
        return new FillRandomDataDtoOut("Random companies successfully filled", companyService.getCompanyCount());
    }

    @Override
    @Transactional
    public Page<CompanyDtoOut> getCompanies(final Pageable pageable) {
        return companyService.getCompanies(pageable).map(companyMapper::toCompanyDtoOut);
    }

    @Override
    @Transactional
    public CompanyDtoOut getByCompanyName(final String title) {
        final var company = companyService.getByCompanyName(title);
        return companyMapper.toCompanyDtoOut(company);
    }

    @Override
    @Transactional
    public CompanyDtoOut createCompany(final CompanyDtoIn companyDtoIn) {
        final var company = companyService.createNewCompany(companyDtoIn.getCompanyName());
        return companyMapper.toCompanyDtoOut(company);
    }

    @Override
    @Transactional
    public void deleteCompany(final CompanyDtoIn companyDtoIn) {
        companyService.deleteByCompanyName(companyDtoIn.getCompanyName());
    }

    @Override
    @Transactional
    public CompanyCountDtoOut getCompanyCount() {
        final var companyCount = companyService.getCompanyCount();
        return new CompanyCountDtoOut(companyCount);
    }

}
