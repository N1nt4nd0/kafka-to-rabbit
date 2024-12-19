package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonCountDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.NoAnyCompanyException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyHobbyException;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyHasHobbyException;
import org.kafka.practice.kafkademo.domain.exception.PersonHaveNotHobbyException;
import org.kafka.practice.kafkademo.domain.mappers.PersonMapper;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonUseCasesImpl implements PersonUseCases {

    private final CompanyService companyService;
    private final PersonService personService;
    private final HobbyService hobbyService;
    private final PersonMapper personMapper;
    private final int pageMaxElementsSize;
    private final Faker dataFaker;

    @Override
    @Transactional
    public PersonDtoOut createPerson(final PersonDtoIn personDtoIn) {
        final var newPerson = personService.createPerson(personDtoIn.getEmail(),
                personDtoIn.getFirstName(), personDtoIn.getLastName());
        return personMapper.toPersonDtoOut(newPerson);
    }

    @Override
    @Transactional
    public PersonDtoOut addHobby(final AddPersonHobbyDtoIn addPersonHobbyDto) {
        final var personByEmail = personService.getByEmail(addPersonHobbyDto.getEmail());
        final var hobby = hobbyService.getByHobbyName(addPersonHobbyDto.getHobbyName());
        if (personByEmail.hasHobby(hobby)) {
            throw new PersonAlreadyHasHobbyException(personByEmail.getEmail(), hobby.getHobbyName());
        }
        final var savedPerson = personService.savePerson(personByEmail.withAddedHobby(hobby));
        return personMapper.toPersonDtoOut(savedPerson);
    }

    @Override
    @Transactional
    public PersonDtoOut removeHobby(final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        final var personByEmail = personService.getByEmail(removePersonHobbyDto.getEmail());
        final var hobby = hobbyService.getByHobbyName(removePersonHobbyDto.getHobbyName());
        if (personByEmail.hasHobby(hobby)) {
            final var savedPerson = personService.savePerson(personByEmail.withRemovedHobby(hobby));
            return personMapper.toPersonDtoOut(savedPerson);
        } else {
            throw new PersonHaveNotHobbyException(personByEmail.getEmail(), hobby.getHobbyName());
        }
    }

    @Override
    @Transactional
    public FillRandomDataDtoOut fillRandomPersons(final FillRandomPersonsDtoIn fillRandomPersonsDtoIn) {
        if (companyService.getCompanyCount() == 0) {
            throw new NoAnyCompanyException();
        }
        if (hobbyService.getHobbyCount() == 0) {
            throw new NoAnyHobbyException();
        }
        final var pageable = PageRequest.of(0, pageMaxElementsSize);
        final var companyList = companyService.getCompanies(pageable).getContent();
        final var hobbyList = hobbyService.getHobbies(pageable).getContent();
        final var random = new Random();
        var filledCount = 0;
        for (int person_i = 0; person_i < fillRandomPersonsDtoIn.getPersonsCount(); person_i++) {
            final var randomHobbies = new ArrayList<Hobby>();
            final var fakerInternet = dataFaker.internet();
            final var fakerName = dataFaker.name();
            Company randomCompany = null;
            if (random.nextBoolean()) {
                randomCompany = companyList.get(random.nextInt(companyList.size()));
            }
            for (int hobby_i = 0; hobby_i < random.nextInt(fillRandomPersonsDtoIn.getHobbiesMaxCount()); hobby_i++) {
                randomHobbies.add(hobbyList.get(random.nextInt(hobbyList.size())));
            }
            final var randomPerson = Person.blankPerson(fakerInternet.emailAddress(),
                            fakerName.firstName(), fakerName.lastName())
                    .withCompany(randomCompany)
                    .withAddedHobbies(randomHobbies);
            personService.savePerson(randomPerson);
            filledCount++;
        }
        return new FillRandomDataDtoOut("Random persons successfully filled", filledCount);
    }

    @Override
    @Transactional
    public Page<PersonDtoOut> getPersons(final Pageable pageable) {
        return personService.getPersons(pageable).map(personMapper::toPersonDtoOut);
    }

    @Override
    @Transactional
    public PersonDtoOut getByEmail(final String email) {
        final var personByEmail = personService.getByEmail(email);
        return personMapper.toPersonDtoOut(personByEmail);
    }

    @Override
    @Transactional
    public void deleteByEmail(final String email) {
        personService.deleteByEmail(email);
    }

    @Override
    @Transactional
    public PersonCountDtoOut getPersonCount() {
        return new PersonCountDtoOut(personService.getPersonCount());
    }

}
