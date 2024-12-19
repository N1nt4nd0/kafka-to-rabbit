package org.kafka.practice.kafkademo.domain.exception;

public class PersonAlreadyHasHobbyException extends RuntimeException {

    public PersonAlreadyHasHobbyException(final String personEmail, final String hobbyName) {
        super(String.format("Person already has hobby. Person email: %s, hobby: %s", personEmail, hobbyName));
    }

}
