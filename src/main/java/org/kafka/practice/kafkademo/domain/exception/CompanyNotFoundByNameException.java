package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class CompanyNotFoundByNameException extends RuntimeException {

    public CompanyNotFoundByNameException(@NonNull final String companyName) {
        super("Can't find company by name: " + companyName);
    }

}
