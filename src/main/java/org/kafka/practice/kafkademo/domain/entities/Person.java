package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
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
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Hobby> hobbies;

    public static Person blankPerson(@NonNull final String email,
                                     @NonNull final String firstName,
                                     @NonNull final String lastName) {
        return new Person(null, email, firstName, lastName, null, List.of());
    }

    public Person withAddedHobby(final Hobby hobby) {
        final var newHobbies = new ArrayList<>(hobbies);
        final var newPerson = new Person(id, email, firstName, lastName, company, newHobbies);
        newHobbies.add(new Hobby(hobby.getId(), hobby.getHobbyName(), newPerson));
        return newPerson;
    }

    public Person withRemovedHobby(final Hobby hobby) {
        final var newHobbies = new ArrayList<>(hobbies);
        final var newPerson = new Person(id, email, firstName, lastName, company, newHobbies);
        newHobbies.remove(hobby);
        return newPerson;
    }

    public Person withCompany(final Company company) {
        return new Person(id, email, firstName, lastName, company, new ArrayList<>(hobbies));
    }

    public Person withoutCompany() {
        return withCompany(null);
    }

    public Person withAddedHobbies(final List<Hobby> addedHobbies) {
        final var newHobbies = new ArrayList<>(hobbies);
        final var newPerson = new Person(id, email, firstName, lastName, company, newHobbies);
        addedHobbies.stream()
                .map(hobby -> new Hobby(hobby.getId(), hobby.getHobbyName(), newPerson))
                .forEach(newHobbies::add);
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
