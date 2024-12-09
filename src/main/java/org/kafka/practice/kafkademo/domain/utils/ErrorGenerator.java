package org.kafka.practice.kafkademo.domain.utils;

import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;

import java.util.Random;

public class ErrorGenerator {

    private final int errorProbability;
    private final Random random;

    public ErrorGenerator(final int errorProbability) {
        this.errorProbability = errorProbability;
        this.random = new Random();
    }

    public void process() {
        if (random.nextInt(100) < errorProbability) {
            throw new RandomGeneratorException();
        }
    }

}
