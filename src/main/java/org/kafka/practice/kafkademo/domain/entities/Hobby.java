package org.kafka.practice.kafkademo.domain.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("hobby")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby {

    @Id
    private ObjectId id;

    @Indexed(name = "hobby_name_index", unique = true, collation = "{locale: 'en', strength: 2}")
    private String hobbyName;

    @Override
    public boolean equals(final Object object) {
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
