package org.kafka.practice.kafkademo.domain.business.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.mappers.HobbyMapper;
import org.kafka.practice.kafkademo.domain.dto.mappers.PersonMapper;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.service.CompanyService;
import org.kafka.practice.kafkademo.domain.service.PersonService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonBusinessServiceImpl implements PersonBusinessService {

    private final ObjectFactory<Person> randomPersonGenerator;
    private final ObjectFactory<Hobby> randomHobbyGenerator;
    private final CompanyService companyService;
    private final PersonService personService;
    private final PersonMapper personMapper;
    private final HobbyMapper hobbyMapper;

    @Override
    public PersonDtoOut updateOrCreate(@NonNull final PersonDtoIn personDtoIn) {
        final var person = personService.updateOrCreate(personDtoIn.getEmail(),
                personDtoIn.getFirstName(), personDtoIn.getLastName());
        return personMapper.toPersonDtoOut(person);
    }

    @Override
    public PersonDtoOut addHobby(@NonNull final AddPersonHobbyDtoIn addPersonHobbyDto) {
        final var person = personService.getByEmail(addPersonHobbyDto.getEmail());
        final var hobby = hobbyMapper.fromPersonAddHobbyDto(addPersonHobbyDto);
        person.addHobby(hobby);
        final var savedPerson = personService.savePerson(person);
        return personMapper.toPersonDtoOut(savedPerson);
    }

    @Override
    public PersonDtoOut removeHobby(@NonNull final RemovePersonHobbyDtoIn removePersonHobbyDto) {
        final var person = personService.getByEmail(removePersonHobbyDto.getEmail());
        final var hobby = hobbyMapper.fromPersonRemoveHobbyDto(removePersonHobbyDto);
        person.removeHobby(hobby);
        final var savedPerson = personService.savePerson(person);
        return personMapper.toPersonDtoOut(savedPerson);
    }

    @Override
    public List<PersonDtoOut> fillRandomPersons(@NonNull final FillRandomPersonsDtoIn fillRandomPersonsDtoIn) {
        return Stream.generate(randomPersonGenerator::getObject)
                .limit(fillRandomPersonsDtoIn.getPersonsCount())
                .map(randomPerson -> {
                    if (new Random().nextBoolean()) {
                        final var randomCompany = companyService
                                .getRandomCompany(fillRandomPersonsDtoIn.getCompaniesBound());
                        randomCompany.hireEmployee(randomPerson);
                    }
                    Stream.generate(randomHobbyGenerator::getObject)
                            .distinct()
                            .limit(new Random().nextInt(fillRandomPersonsDtoIn.getMaxHobbiesCount()))
                            .forEach(randomPerson::addHobby);
                    return personService.savePerson(randomPerson);
                })
                .map(personMapper::toPersonDtoOut).toList();
    }

    @Override
    public Page<PersonDtoOut> getPersons(@NonNull final Pageable pageable) {
        return personService.getPersons(pageable).map(personMapper::toPersonDtoOut);
    }

    @Override
    public PersonDtoOut getByEmail(@NonNull final String email) {
        final var person = personService.getByEmail(email);
        return personMapper.toPersonDtoOut(person);
    }

    @Override
    public void deleteByEmail(@NonNull final String email) {
        personService.deleteByEmail(email);
    }

}
