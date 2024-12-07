package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class CustomKafkaException extends RuntimeException {

    public CustomKafkaException(@NonNull final String message) {
        super(message);
    }

}
