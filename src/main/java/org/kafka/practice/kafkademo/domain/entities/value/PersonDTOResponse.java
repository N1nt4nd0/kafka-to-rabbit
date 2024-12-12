package org.kafka.practice.kafkademo.domain.entities.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class PersonDTOResponse {

    @Setter
    private boolean fail;

    private final String email;
    private final String firstName;
    private final String lastName;

    @JsonCreator
    public PersonDTOResponse(@JsonProperty("email") @NonNull final String email,
                             @JsonProperty("firstName") @NonNull final String firstName,
                             @JsonProperty("lastName") @NonNull final String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}