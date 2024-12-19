package org.kafka.practice.kafkademo.domain.exception;

public class PersonNotFoundByEmailException extends RuntimeException {

    public PersonNotFoundByEmailException(final String email) {
        super("Can't find person by email: " + email);
    }

}
