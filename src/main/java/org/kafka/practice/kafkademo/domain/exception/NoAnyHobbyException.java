package org.kafka.practice.kafkademo.domain.exception;

public class NoAnyHobbyException extends RuntimeException {

    public NoAnyHobbyException() {
        super("There is no any hobbies in database. Fill hobbies first");
    }

}
