package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonBusinessService {

    PersonDtoOut updateOrCreate(PersonDtoIn personDtoIn);

    PersonDtoOut addHobby(AddPersonHobbyDtoIn addPersonHobbyDtoIn);

    PersonDtoOut removeHobby(RemovePersonHobbyDtoIn removePersonHobbyDtoIn);

    List<PersonDtoOut> fillRandomPersons(FillRandomPersonsDtoIn fillRandomPersonsDtoIn);

    Page<PersonDtoOut> getPersons(Pageable pageable);

    PersonDtoOut getByEmail(String email);

    void deleteByEmail(String email);

}
