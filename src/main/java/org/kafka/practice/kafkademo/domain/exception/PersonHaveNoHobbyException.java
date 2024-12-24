package org.kafka.practice.kafkademo.domain.exception;

public class PersonHaveNoHobbyException extends RuntimeException {

    public PersonHaveNoHobbyException(final String personEmail, final String hobbyName) {
        super(String.format("Person has no hobbies found. Person email: %s, hobby name: %s", personEmail, hobbyName));
    }

}
