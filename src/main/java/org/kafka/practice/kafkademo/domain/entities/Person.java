package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "persons", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @JoinColumn(name = "job_id")
    private Job job;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Hobby> hobbies;

    public Person() {
        this.hobbies = new ArrayList<>();
    }

    public Person(@NonNull final String email, @NonNull final String firstName,
                  @NonNull final String lastName) {
        this();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(@NonNull final UUID id, @NonNull final String email, @NonNull final String firstName,
                  @NonNull final String lastName) {
        this();
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

    public boolean haveJob() {
        return job != null;
    }

    public boolean haveHobbies() {
        return !hobbies.isEmpty();
    }

    public void removeJob() {
        job = null;
    }

    public void setJob(@NonNull final Job job) {
        this.job = job;
    }

    public void addHobby(@NonNull final Hobby hobby) {
        hobbies.add(hobby);
        hobby.setPerson(this);
    }

    public void removeHobby(@NonNull final Hobby hobby) {
        hobbies.remove(hobby);
        hobby.removePerson();
    }

}