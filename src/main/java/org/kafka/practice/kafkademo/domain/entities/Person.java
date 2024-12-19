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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Entity
@Table(name = "person")
@Getter
@ToString
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private final UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private final String email;

    @Column(name = "first_name", nullable = false)
    private final String firstName;

    @Column(name = "last_name", nullable = false)
    private final String lastName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private final Company company;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Hobby> hobbies;

    protected Person() {
        this.id = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.company = null;
        this.hobbies = new ArrayList<>();
    }

    private Person(final UUID id,
                   final String email,
                   final String firstName,
                   final String lastName,
                   final Company company,
                   final List<Hobby> hobbies) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        if (company == null) {
            this.company = null;
        } else {
            this.company = new Company(company.getId(), company.getCompanyName());
        }
        this.hobbies = hobbies.stream()
                .map(hobby -> new Hobby(hobby.getId(), hobby.getHobbyName(), this))
                .toList();
    }

    public Person withAddedHobbies(@NonNull final List<Hobby> hobbiesToAdd) {
        return new Person(id, email, firstName, lastName, company,
                Stream.of(hobbies, hobbiesToAdd).flatMap(Collection::stream).toList());
    }

    public Person withAddedHobby(@NonNull final Hobby hobbyToAdd) {
        final var updatedHobbies = new ArrayList<>(hobbies);
        updatedHobbies.add(hobbyToAdd);
        return new Person(id, email, firstName, lastName, company, updatedHobbies);
    }

    public Person withRemovedHobby(@NonNull final Hobby hobbyToRemove) {
        final var updatedHobbies = new ArrayList<>(hobbies);
        updatedHobbies.remove(hobbyToRemove);
        return new Person(id, email, firstName, lastName, company, updatedHobbies);
    }

    public Person withCompany(final Company company) {
        return new Person(id, email, firstName, lastName, company, hobbies);
    }

    public Person withoutCompany() {
        return withCompany(null);
    }

    public boolean haveJob() {
        return company != null;
    }

    public boolean haveHobbies() {
        return !hobbies.isEmpty();
    }

    public static Person blankPerson(@NonNull final String email,
                                     @NonNull final String firstName,
                                     @NonNull final String lastName) {
        return new Person(null, email, firstName, lastName, null, List.of());
    }

}