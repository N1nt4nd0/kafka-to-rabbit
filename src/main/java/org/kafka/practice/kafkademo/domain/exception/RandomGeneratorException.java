package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class RandomGeneratorException extends CustomKafkaException {

    public RandomGeneratorException(@NonNull final String message) {
        super(message);
    }

}
