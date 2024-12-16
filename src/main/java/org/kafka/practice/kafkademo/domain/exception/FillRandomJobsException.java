package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class FillRandomJobsException extends RuntimeException {

    public FillRandomJobsException(@NonNull final String message) {
        super(message);
    }

}
