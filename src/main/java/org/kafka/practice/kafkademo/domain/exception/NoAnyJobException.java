package org.kafka.practice.kafkademo.domain.exception;

public class NoAnyJobException extends RuntimeException {

    public NoAnyJobException() {
        super("There is no any jobs in database");
    }

}
