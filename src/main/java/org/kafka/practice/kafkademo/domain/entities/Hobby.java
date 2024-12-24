package org.kafka.practice.kafkademo.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "hobby")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hobby_name", nullable = false)
    private String hobbyName;

    @Override
    public boolean equals(Object object) {
        if (object instanceof Hobby hobby) {
            return Objects.equals(hobbyName, hobby.hobbyName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hobbyName);
    }

}
