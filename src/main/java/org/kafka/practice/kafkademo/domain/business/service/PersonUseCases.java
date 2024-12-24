package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonCountDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonUseCases {

    PersonDtoOut createPerson(PersonDtoIn personDtoIn);

    PersonDtoOut addHobby(AddPersonHobbyDtoIn addPersonHobbyDtoIn);

    PersonDtoOut removeHobby(RemovePersonHobbyDtoIn removePersonHobbyDtoIn);

    FillRandomDataDtoOut fillRandomPersons(FillRandomPersonsDtoIn fillRandomPersonsDtoIn);

    Page<PersonDtoOut> getPersons(Pageable pageable);

    PersonDtoOut getByEmail(String email);

    void deleteByEmail(String email);

    TruncateTableDtoOut truncatePersons();

    PersonCountDtoOut getPersonCount();

}
