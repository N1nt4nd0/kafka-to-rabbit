package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class PersonNotFoundByEmailException extends RuntimeException {

    public PersonNotFoundByEmailException(@NonNull final String email) {
        super("Can't find person by email: " + email);
    }

}
