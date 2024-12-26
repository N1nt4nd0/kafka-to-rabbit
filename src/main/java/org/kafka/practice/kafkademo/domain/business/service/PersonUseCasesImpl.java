package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonHobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
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
        final var newPerson = personService.createPerson(personDtoIn.getEmail(),
                personDtoIn.getFirstName(), personDtoIn.getLastName());
        return personMapper.toPersonDtoOut(newPerson);
    }

    @Override
    @Transactional
    public CompanyManagementDtoOut manageCompany(final CompanyManagementDtoIn companyManagementDtoIn) {
        final var personByEmail = personService.getByEmail(companyManagementDtoIn.getPersonEmail());
        final var companyByName = companyService.getByCompanyName(companyManagementDtoIn.getCompanyName());
        Person person;
        String message;
        switch (companyManagementDtoIn.getManagementType()) {
            case HIRE -> {
                if (personByEmail.isCompanyEmployee(companyByName)) {
                    throw new CompanyManagementException("Person already hired at company");
                }
                person = personByEmail.withCompany(companyByName);
                message = "Person was hired successfully";
            }
            case DISMISS -> {
                if (!personByEmail.isCompanyEmployee(companyByName)) {
                    throw new CompanyManagementException("Person is not company employee");
                }
                person = personByEmail.withoutCompany();
                message = "Person was dismissed successfully";
            }
            default -> throw new CompanyManagementException("Unimplemented management type");
        }
        final var savedPerson = personService.savePerson(person);
        return new CompanyManagementDtoOut(savedPerson.getEmail(), companyByName.getCompanyName(), message);
    }

    @Override
    @Transactional
    public PersonHobbyDtoOut addHobby(final AddPersonHobbyDtoIn addPersonHobbyDto) {
        final var personByEmail = personService.getByEmail(addPersonHobbyDto.getEmail());
        final var hobby = hobbyService.getByHobbyName(addPersonHobbyDto.getHobbyName());
        if (personByEmail.hasHobby(hobby)) {
            throw new PersonAlreadyHasHobbyException(personByEmail.getEmail(), hobby.getHobbyName());
        }
        personService.savePerson(personByEmail.withAddedHobby(hobby));
        return new PersonHobbyDtoOut("Hobby added successfully");
    }

    @Override
    @Transactional
    public PersonHobbyDtoOut removeHobby(final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        final var personByEmail = personService.getByEmail(removePersonHobbyDto.getEmail());
        final var hobby = hobbyService.getByHobbyName(removePersonHobbyDto.getHobbyName());
        if (personByEmail.hasHobby(hobby)) {
            personService.savePerson(personByEmail.withRemovedHobby(hobby));
            return new PersonHobbyDtoOut("Hobby removed successfully");
        }
        throw new PersonHaveNoHobbyException(personByEmail.getEmail(), hobby.getHobbyName());
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
