package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.mappers.HobbyMapper;
import org.kafka.practice.kafkademo.domain.dto.mappers.PersonMapper;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonCountDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.HobbyNotFoundException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyCompanyException;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.HobbyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonUseCasesImpl implements PersonUseCases {

    private final CompanyService companyService;
    private final PersonService personService;
    private final HobbyService hobbyService;
    private final PersonMapper personMapper;
    private final int pageMaxElementsSize;
    private final HobbyMapper hobbyMapper;

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
        final var hobby = new Hobby(null, addPersonHobbyDto.getHobbyName(), personByEmail);
        final var savedPerson = personService.savePerson(personByEmail.withAddedHobby(hobby));
        return personMapper.toPersonDtoOut(savedPerson);
    }

    @Override
    @Transactional
    public PersonDtoOut removeHobby(final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        final var personByEmail = personService.getByEmail(removePersonHobbyDto.getEmail());
        final var hobby = hobbyMapper.fromPersonRemoveHobbyDto(removePersonHobbyDto);
        if (personByEmail.hasHobby(hobby)) {
            hobbyService.deleteHobby(hobby);
            final var savedPerson = personService.savePerson(personByEmail.withRemovedHobby(hobby));
            return personMapper.toPersonDtoOut(savedPerson);
        } else {
            throw new HobbyNotFoundException(hobby.getHobbyName(), hobby.getId());
        }
    }

    @Override
    @Transactional
    public FillRandomDataDtoOut fillRandomPersons(final FillRandomPersonsDtoIn fillRandomPersonsDtoIn) {
        if (companyService.getCompanyCount() == 0) {
            throw new NoAnyCompanyException();
        }
        final var companyPage = companyService.getCompanies(PageRequest.of(0, pageMaxElementsSize));
        final var companyList = companyPage.getContent();
        final var random = new Random();
        final var faker = new Faker();
        var filledCount = 0;
        for (int i = 0; i < fillRandomPersonsDtoIn.getPersonsCount(); i++) {
            final var email = faker.internet().emailAddress();
            final var fakerHobby = faker.hobby();
            final var fakerName = faker.name();
            Company randomCompany = null;
            if (random.nextBoolean()) {
                randomCompany = companyList.get(random.nextInt(companyList.size()));
            }
            final var randomHobbies = Stream.generate(fakerHobby::activity)
                    .distinct()
                    .limit(random.nextInt(fillRandomPersonsDtoIn.getHobbiesMaxCount()))
                    .map(Hobby::blankHobby)
                    .toList();
            final var randomPerson = Person.blankPerson(email, fakerName.firstName(), fakerName.lastName())
                    .withCompany(randomCompany)
                    .withAddedHobbies(randomHobbies);
            final var savedPerson = personService.savePerson(randomPerson);
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
        final var personCount = personService.getPersonCount();
        return new PersonCountDtoOut(personCount);
    }

}
