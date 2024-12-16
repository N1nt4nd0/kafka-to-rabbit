package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class FillRandomCompaniesException extends RuntimeException {

    public FillRandomCompaniesException(@NonNull final String message) {
        super(message);
    }

}
