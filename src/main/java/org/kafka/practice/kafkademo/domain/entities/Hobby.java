package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hobbies", schema = "public")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hobby_name", nullable = false)
    private String hobbyName;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

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

}
