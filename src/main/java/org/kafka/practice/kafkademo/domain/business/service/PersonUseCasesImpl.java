package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.PersonHobbyResultDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.CompanyManagementException;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyHasHobbyException;
import org.kafka.practice.kafkademo.domain.exception.PersonHaveNoHobbyException;
import org.kafka.practice.kafkademo.domain.mappers.PersonMapper;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonUseCasesImpl implements PersonUseCases {

    private final CompanyService companyService;
    private final PersonService personService;
    private final HobbyService hobbyService;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public FillRandomDataDtoOut fillRandomPersons(final FillRandomPersonsDtoIn fillRandomPersonsDtoIn) {
        personService.validateGenerationCount(fillRandomPersonsDtoIn.getPersonCount(),
                fillRandomPersonsDtoIn.getHobbyMaxCount());
        return new FillRandomDataDtoOut("Random persons successfully filled",
                personService.generateNRandomPersons(fillRandomPersonsDtoIn.getPersonCount(),
                        fillRandomPersonsDtoIn.getHobbyMaxCount()));
    }

    @Override
    @Transactional
    public PersonDtoOut createPerson(final PersonDtoIn personDtoIn) {
        return personMapper.toPersonDtoOut(personService.createPerson(personDtoIn.getEmail(),
                personDtoIn.getFirstName(), personDtoIn.getLastName()));
    }

    @Override
    @Transactional
    public CompanyManagementDtoOut manageCompany(final CompanyManagementDtoIn companyManagementDtoIn) {
        final var personByEmail = personService.getByEmail(companyManagementDtoIn.getPersonEmail());
        final var companyByName = companyService.getByCompanyName(companyManagementDtoIn.getCompanyName());
        return managePersonCompany(companyManagementDtoIn, personByEmail, companyByName);
    }

    private CompanyManagementDtoOut managePersonCompany(final CompanyManagementDtoIn companyManagementDtoIn,
                                                        final Person person, final Company company) {
        switch (companyManagementDtoIn.getManagementType()) {
            case HIRE -> {
                return hirePerson(person, company);
            }
            case DISMISS -> {
                return dismissEmployee(person, company);
            }
            default -> throw new CompanyManagementException("Unimplemented management type");
        }
    }

    private CompanyManagementDtoOut hirePerson(final Person personByEmail, final Company companyByName) {
        if (personByEmail.isCompanyEmployee(companyByName)) {
            throw new CompanyManagementException("Person already hired at company");
        }
        final var savedPerson = personService.savePerson(personByEmail.withCompany(companyByName));
        return new CompanyManagementDtoOut(savedPerson.getEmail(), companyByName.getCompanyName(),
                "Person was hired successfully");
    }

    private CompanyManagementDtoOut dismissEmployee(final Person person, final Company company) {
        if (person.isCompanyEmployee(company)) {
            final var savedPerson = personService.savePerson(person.withoutCompany());
            return new CompanyManagementDtoOut(savedPerson.getEmail(), company.getCompanyName(),
                    "Person was dismissed successfully");
        }
        throw new CompanyManagementException("Person is not company employee");
    }

    @Override
    @Transactional
    public PersonHobbyResultDtoOut addHobby(final AddPersonHobbyDtoIn addPersonHobbyDto) {
        final var personByEmail = personService.getByEmail(addPersonHobbyDto.getEmail());
        final var hobby = hobbyService.getByHobbyName(addPersonHobbyDto.getHobbyName());
        if (personByEmail.hasHobby(hobby)) {
            throw new PersonAlreadyHasHobbyException(personByEmail.getEmail(), hobby.getHobbyName());
        }
        personService.savePerson(personByEmail.withAddedHobby(hobby));
        return new PersonHobbyResultDtoOut("Hobby added successfully");
    }

    @Override
    @Transactional
    public PersonHobbyResultDtoOut removeHobby(final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        final var personByEmail = personService.getByEmail(removePersonHobbyDto.getEmail());
        final var hobbyByName = hobbyService.getByHobbyName(removePersonHobbyDto.getHobbyName());
        if (personByEmail.hasHobby(hobbyByName)) {
            personService.savePerson(personByEmail.withRemovedHobby(hobbyByName));
            return new PersonHobbyResultDtoOut("Hobby removed successfully");
        }
        throw new PersonHaveNoHobbyException(personByEmail.getEmail(), hobbyByName.getHobbyName());
    }

    @Override
    @Transactional
    public Page<PersonDtoOut> getPersons(final Pageable pageable) {
        return personService.getPersons(pageable).map(personMapper::toPersonDtoOut);
    }

    @Override
    @Transactional
    public TruncateTableDtoOut truncatePersons() {
        personService.truncatePersonsTable();
        return new TruncateTableDtoOut("Persons table successfully truncated");
    }

}
