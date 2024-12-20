package org.kafka.practice.kafkademo.domain.exception;

public class NoAnyCompanyException extends RuntimeException {

    public NoAnyCompanyException() {
        super("There is no any companies in database. Fill companies first");
    }

}
