package org.kafka.practice.kafkademo.domain.exception;

public class PersonHaveNotHobbyException extends RuntimeException {

    public PersonHaveNotHobbyException(final String personEmail, final String hobbyName) {
        super(String.format("Person has no hobbies found. Person email: %s, hobby name: %s", personEmail, hobbyName));
    }

}
