package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.CascadeType;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_hobby",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private Set<Hobby> hobbies;

    public static Person blankPerson(@NonNull final String email,
                                     @NonNull final String firstName,
                                     @NonNull final String lastName) {
        return new Person(null, email, firstName, lastName, null, Set.of());
    }

    public Person withAddedHobby(final Hobby hobby) {
        final var newHobbies = new HashSet<>(hobbies);
        final var newPerson = new Person(id, email, firstName, lastName, company, newHobbies);
        newHobbies.add(hobby);
        return newPerson;
    }

    public Person withRemovedHobby(final Hobby hobby) {
        final var newHobbies = new HashSet<>(hobbies);
        final var newPerson = new Person(id, email, firstName, lastName, company, newHobbies);
        newHobbies.remove(hobby);
        return newPerson;
    }

    public Person withCompany(final Company company) {
        return new Person(id, email, firstName, lastName, company, new HashSet<>(hobbies));
    }

    public Person withoutCompany() {
        return withCompany(null);
    }

    public Person withAddedHobbies(final Set<Hobby> addedHobbies) {
        final var newHobbies = new HashSet<>(hobbies);
        final var newPerson = new Person(id, email, firstName, lastName, company, newHobbies);
        newHobbies.addAll(addedHobbies);
        return newPerson;
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

}
