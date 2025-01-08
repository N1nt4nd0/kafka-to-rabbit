package org.kafka.practice.kafkademo.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.dto.hobby.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.person.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonMapperImpl implements PersonMapper {

    private final HobbyMapper hobbyMapper;

    @Override
    public PersonDtoOut toPersonDtoOut(final Person person) {
        return new PersonDtoOut(person.getId().toHexString(), person.getEmail(), person.getFirstName(),
                person.getLastName(), getCompanyName(person), getHobbyDtoList(person));
    }

    private List<HobbyDtoOut> getHobbyDtoList(final Person person) {
        return person.getHobbies().stream().map(hobbyMapper::toHobbyDtoOut).toList();
    }

    private String getCompanyName(final Person person) {
        if (person.hasJob()) {
            return person.getCompany().getCompanyName();
        }
        return "";
    }

}
