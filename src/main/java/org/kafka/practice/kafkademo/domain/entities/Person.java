package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "persons", schema = "public")
@Getter
@ToString //TODO exclude for OneToMany, ManyToOne fields
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public Person(final UUID id, @NonNull final String email, @NonNull final String firstName,
                  @NonNull final String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected void setId(@NonNull final UUID id) {
        this.id = id;
    }

    protected void setEmail(@NonNull final String email) {
        this.email = email;
    }

    protected void setFirstName(@NonNull final String firstName) {
        this.firstName = firstName;
    }

    protected void setLastName(@NonNull final String lastName) {
        this.lastName = lastName;
    }

}