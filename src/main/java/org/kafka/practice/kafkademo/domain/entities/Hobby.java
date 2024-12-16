package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "hobbies", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hobby_name", nullable = false)
    private String hobbyName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Hobby() {
    }

    public Hobby(@NonNull final String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public Hobby(@NonNull final Long id, @NonNull final String hobbyName) {
        this.id = id;
        this.hobbyName = hobbyName;
    }

    protected void setId(@NonNull final Long id) {
        this.id = id;
    }

    protected void setHobbyName(@NonNull final String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public void setPerson(@NonNull final Person person) {
        this.person = person;
    }

    public void removePerson() {
        person = null;
    }

}
