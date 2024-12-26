package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "person_hobby",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
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
