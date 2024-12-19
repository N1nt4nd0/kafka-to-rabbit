package org.kafka.practice.kafkademo.domain.dto.mappers;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapperImpl implements PersonMapper {

    private final HobbyMapper hobbyMapper;

    @Override
    public PersonDtoOut toPersonDtoOut(final Person person) {
        var companyName = "";
        if (person.hasJob()) {
            companyName = person.getCompany().getCompanyName();
        }
        final var hobbyList = person.getHobbies().stream().map(hobbyMapper::toHobbyDtoOut).toList();
        return new PersonDtoOut(person.getId(), person.getEmail(), person.getFirstName(),
                person.getLastName(), companyName, hobbyList);
    }

}
