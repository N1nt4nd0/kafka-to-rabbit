package org.kafka.practice.kafkademo.domain.utils;

import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;

import java.util.Random;

public class ExceptionGenerator {

    private final int errorProbability;
    private final Random random;

    public ExceptionGenerator(final int errorProbability) {
        this.errorProbability = errorProbability;
        this.random = new Random();
    }

    public void generateRandomGeneratorException() {
        if (random.nextInt(100) < errorProbability) {
            throw new RandomGeneratorException();
        }
    }

}
