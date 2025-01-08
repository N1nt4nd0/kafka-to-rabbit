package org.kafka.practice.kafkademo.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collation = "person")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @DBRef
    private Company company;

    @DBRef
    private List<Hobby> hobbies;

    public Person withAddedHobby(final Hobby hobby) {
        final var newHobbies = new ArrayList<>(hobbies);
        newHobbies.add(hobby);
        return new Person(id, email, firstName, lastName, company, newHobbies);
    }

    public Person withRemovedHobby(final Hobby hobby) {
        final var newHobbies = new ArrayList<>(hobbies);
        newHobbies.remove(hobby);
        return new Person(id, email, firstName, lastName, company, newHobbies);
    }

    public Person withCompany(final Company company) {
        return new Person(id, email, firstName, lastName, company, new ArrayList<>(hobbies));
    }

    public Person withoutCompany() {
        return withCompany(null);
    }

    public Person withAddedHobbies(final List<Hobby> addedHobbies) {
        final var newHobbies = new ArrayList<>(hobbies);
        newHobbies.addAll(addedHobbies);
        return new Person(id, email, firstName, lastName, company, newHobbies);
    }

    public boolean isCompanyEmployee(final Company company) {
        return hasJob() && this.company.equals(company);
    }

    public boolean hasJob() {
        return company != null;
    }

    public boolean hasHobbies() {
        return !hobbies.isEmpty();
    }

    public boolean hasHobby(final Hobby hobby) {
        return hasHobbies() && hobbies.contains(hobby);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Person person) {
            return Objects.equals(email, person.email);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

}
