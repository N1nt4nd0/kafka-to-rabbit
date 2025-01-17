package org.kafka.practice.kafkademo.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.kafka.practice.kafkademo.domain.config.cache.CacheKeyBuilder;
import org.kafka.practice.kafkademo.domain.config.web.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.kafka.practice.kafkademo.domain.exception.FillRandomDataException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyCompanyException;
import org.kafka.practice.kafkademo.domain.exception.NoAnyHobbyException;
import org.kafka.practice.kafkademo.domain.exception.PersonAlreadyExistException;
import org.kafka.practice.kafkademo.domain.exception.PersonNotFoundByEmailException;
import org.kafka.practice.kafkademo.domain.repository.CompanyRepository;
import org.kafka.practice.kafkademo.domain.repository.HobbyRepository;
import org.kafka.practice.kafkademo.domain.repository.PersonRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;
    private final HobbyRepository hobbyRepository;
    private final WebPagesConfig webPagesConfig;
    private final Faker dataFaker;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public long generateNRandomPersons(final int personCount, final int personMaxHobbyCount) {
        final var pageable = PageRequest.of(0, webPagesConfig.getPageMaxElementsSize());
        final var companyList = companyRepository.findAll(pageable).getContent();
        final var hobbyList = hobbyRepository.findAll(pageable).getContent();
        final var random = new Random();
        return Stream.generate(() -> dataFaker.internet().emailAddress())
                .limit(personCount)
                .distinct()
                .filter(email -> personRepository.findByEmailIgnoreCase(email).isEmpty())
                .map(email -> {
                    final var fakerName = dataFaker.name();
                    Company randomCompany = null;
                    if (random.nextBoolean()) {
                        randomCompany = companyList.get(random.nextInt(companyList.size()));
                    }
                    final var randomHobbies = random.ints(0, hobbyList.size())
                            .limit(random.nextInt(personMaxHobbyCount + 1))
                            .distinct()
                            .mapToObj(hobbyList::get)
                            .toList();
                    return new Person(null, email, fakerName.firstName(), fakerName.lastName(),
                            randomCompany, randomHobbies);
                })
                .map(this::savePerson)
                .toList()
                .size();
    }

    @Override
    @Transactional
    public void validateGenerationCount(final int requestedCount, final int requestedHobbyCount) {
        if (companyRepository.count() == 0) {
            throw new NoAnyCompanyException();
        }
        if (hobbyRepository.count() == 0) {
            throw new NoAnyHobbyException();
        }
        if (requestedHobbyCount < 0) {
            throw new FillRandomDataException("Persons hobbies count must be great than 0");
        }
        if (requestedCount < 1) {
            throw new FillRandomDataException("Persons count must be great than 1");
        }
        if (requestedCount > 1000) {
            throw new FillRandomDataException("Persons count must be less than 1000");
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public Person createPerson(final String email, final String firstName, final String lastName) {
        final var personByEmailOptional = personRepository.findByEmailIgnoreCase(email);
        if (personByEmailOptional.isPresent()) {
            throw new PersonAlreadyExistException(email);
        }
        return savePerson(new Person(null, email, firstName, lastName, null, List.of()));
    }

    @Override
    @Transactional
    public Page<Person> getPersons(final Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Person getByEmail(final String email) {
        return personRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new PersonNotFoundByEmailException(email));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public Person savePerson(final Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public void deletePerson(final Person person) {
        personRepository.delete(person);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public void deleteByEmail(final String email) {
        deletePerson(getByEmail(email));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyBuilder.PERSON_PAGE_CACHE_NAME, allEntries = true)
    public void truncatePersonsTable() {
        personRepository.deleteAll();
    }

    @Override
    @Transactional
    public long getPersonCount() {
        return personRepository.count();
    }

}
