package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.AddPersonHobbyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.CompanyManagementDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.PersonHobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.RemovePersonHobbyDtoIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonUseCases {

    FillRandomDataDtoOut fillRandomPersons(FillRandomPersonsDtoIn fillRandomPersonsDtoIn);

    PersonDtoOut createPerson(PersonDtoIn personDtoIn);

    CompanyManagementDtoOut manageCompany(CompanyManagementDtoIn companyManagementDtoIn);

    PersonHobbyDtoOut addHobby(AddPersonHobbyDtoIn addPersonHobbyDtoIn);

    PersonHobbyDtoOut removeHobby(RemovePersonHobbyDtoIn removePersonHobbyDtoIn);

    Page<PersonDtoOut> getPersons(Pageable pageable);

    TruncateTableDtoOut truncatePersons();

}
