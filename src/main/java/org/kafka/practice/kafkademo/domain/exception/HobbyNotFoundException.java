package org.kafka.practice.kafkademo.domain.exception;

public class HobbyNotFoundException extends RuntimeException {

    public HobbyNotFoundException(final String hobbyName, final long hobbyId) {
        super(String.format("Cant't find hobby. Id: %s, name: %s", hobbyId, hobbyName));
    }

}
