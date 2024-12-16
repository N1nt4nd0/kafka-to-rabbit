package org.kafka.practice.kafkademo.domain.dto.mappers;

import lombok.NonNull;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.entities.Hobby;
import org.kafka.practice.kafkademo.domain.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDtoOut toPersonDtoOut(@NonNull final Person person) {
        var jobTitle = "";
        if (person.haveJob()) {
            jobTitle = person.getJob().getJobTitle();
        }
        final var hobbyList = person.getHobbies().stream().map(Hobby::getHobbyName).toList();
        return new PersonDtoOut(person.getId(), person.getEmail(), person.getFirstName(),
                person.getLastName(), jobTitle, hobbyList);
    }

}
