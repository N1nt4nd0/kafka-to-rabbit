package org.kafka.practice.kafkademo.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.kafka.practice.kafkademo.domain.utils.ValidationHelper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document("person")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    private ObjectId id;

    @Indexed(name = "email_index", unique = true, collation = "{locale: 'en', strength: 2}")
    private String email;

    private String firstName;

    private String lastName;

    @DBRef(lazy = true)
    private Company company;

    @DBRef(lazy = true)
    private List<Hobby> hobbies = new ArrayList<>();

    public Person withAddedHobby(@NonNull final Hobby hobby) {
        final var newHobbies = new ArrayList<>(hobbies);
        newHobbies.add(hobby);
        return new Person(id, email, firstName, lastName, company, newHobbies);
    }

    public Person withRemovedHobby(@NonNull final Hobby hobby) {
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

    public Person withAddedHobbies(@NonNull final List<Hobby> addedHobbies) {
        ValidationHelper.checkListForNullElements(addedHobbies);
        final var newHobbies = new ArrayList<>(hobbies);
        newHobbies.addAll(addedHobbies);
        return new Person(id, email, firstName, lastName, company, newHobbies);
    }

    public boolean isCompanyEmployee(@NonNull final Company company) {
        return hasJob() && this.company.equals(company);
    }

    public boolean hasJob() {
        return company != null;
    }

    public boolean hasHobbies() {
        return !hobbies.isEmpty();
    }

    public boolean hasHobby(@NonNull final Hobby hobby) {
        return hasHobbies() && hobbies.contains(hobby);
    }

    @Override
    public boolean equals(final Object object) {
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
