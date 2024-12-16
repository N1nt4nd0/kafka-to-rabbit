package org.kafka.practice.kafkademo.domain.business.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.EmployeeManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.mappers.CompanyMapper;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyBusinessServiceImpl implements CompanyBusinessService {

    private final CompanyService companyService;
    private final PersonService personService;
    private final CompanyMapper companyMapper;

    @Override
    public EmployeeManagementDtoOut manageEmployee(@NonNull final EmployeeManagementDtoIn employeeManagementDtoIn) {
        final var person = personService.getByEmail(employeeManagementDtoIn.getPersonEmail());
        final var company = companyService.getByCompanyName(employeeManagementDtoIn.getCompanyName());
        String message;
        if (employeeManagementDtoIn.getManagementType() == EmployeeManagementDtoIn.ManagementType.HIRE) {
            company.hireEmployee(person);
            message = "Employee was hired successfully";
        } else {
            company.dismissEmployee(person);
            message = "Employee was dismissed successfully";
        }
        final var savedPerson = personService.savePerson(person);
        return new EmployeeManagementDtoOut(savedPerson.getEmail(), company.getCompanyName(), message);
    }

    @Override
    public List<CompanyDtoOut> fillRandomCompanies(@NonNull final FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn) {
        return companyService.fillRandomCompanies(fillRandomCompaniesDtoIn.getCompaniesCount()).stream()
                .map(companyMapper::toCompanyDtoOut).toList();
    }

    @Override
    public Page<CompanyDtoOut> getCompanies(@NonNull final Pageable pageable) {
        return companyService.getCompanies(pageable).map(companyMapper::toCompanyDtoOut);
    }

    @Override
    public CompanyDtoOut getByCompanyName(@NonNull final String title) {
        final var company = companyService.getByCompanyName(title);
        return companyMapper.toCompanyDtoOut(company);
    }

    @Override
    public CompanyDtoOut createCompany(@NonNull final CompanyDtoIn companyDtoIn) {
        final var company = companyService.createNewCompany(companyDtoIn.getCompanyName());
        return companyMapper.toCompanyDtoOut(company);
    }

    @Override
    public void deleteCompany(@NonNull final CompanyDtoIn companyDtoIn) {
        companyService.deleteByCompanyName(companyDtoIn.getCompanyName());
    }

}
