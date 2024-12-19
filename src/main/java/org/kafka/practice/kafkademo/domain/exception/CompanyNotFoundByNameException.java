package org.kafka.practice.kafkademo.domain.exception;

public class CompanyNotFoundByNameException extends RuntimeException {

    public CompanyNotFoundByNameException(final String companyName) {
        super("Can't find company by name: " + companyName);
    }

}
