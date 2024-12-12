package org.kafka.practice.kafkademo.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class PersonDTOResponse {

    @Setter
    private boolean fail;

    private final UUID id;
    private final String email;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public PersonDTOResponse(@JsonProperty("id") final UUID id,
                             @JsonProperty("email") final String email,
                             @JsonProperty("firstName") final String firstName,
                             @JsonProperty("lastName") final String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}