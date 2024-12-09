package org.kafka.practice.kafkademo.domain.exception;

public class RandomGeneratorException extends RuntimeException {

    public RandomGeneratorException() {
        super("Random generator exception occurred");
    }

}
