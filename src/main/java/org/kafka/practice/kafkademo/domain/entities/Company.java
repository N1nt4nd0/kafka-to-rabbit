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

@Document("company")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    private ObjectId id;

    @Indexed(name = "company_name_index", unique = true, collation = "{locale: 'en', strength: 2}")
    private String companyName;

    @Override
    public boolean equals(Object object) {
        if (object instanceof Company company) {
            return Objects.equals(companyName, company.companyName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(companyName);
    }

}
