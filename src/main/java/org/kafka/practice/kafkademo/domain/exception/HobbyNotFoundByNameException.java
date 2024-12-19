package org.kafka.practice.kafkademo.domain.exception;

public class HobbyNotFoundByNameException extends RuntimeException {

    public HobbyNotFoundByNameException(final String hobbyName) {
        super("Can't find hobby by name: " + hobbyName);
    }

}
