package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "persons", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Hobby> hobbies = new ArrayList<>();

    public Person(@NonNull final String email, @NonNull final String firstName,
                  @NonNull final String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(@NonNull final UUID id, @NonNull final String email, @NonNull final String firstName,
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

    public void setJob(@NonNull final Job job) {
        this.job = job;
    }

    public void addHobby(@NonNull final Hobby hobby) {
        hobbies.add(hobby);
        hobby.setPerson(this);
    }

    public void removeHobby(@NonNull final Hobby hobby) {
        hobbies.remove(hobby);
    }

}