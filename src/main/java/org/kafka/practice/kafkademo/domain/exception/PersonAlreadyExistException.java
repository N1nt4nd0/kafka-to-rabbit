package org.kafka.practice.kafkademo.domain.exception;

public class PersonAlreadyExistException extends RuntimeException {

    public PersonAlreadyExistException(final String email) {
        super("Person already exist with specified email: " + email);
    }

}
