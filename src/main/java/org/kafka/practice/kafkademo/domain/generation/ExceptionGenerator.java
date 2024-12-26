package org.kafka.practice.kafkademo.domain.generation;

import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;

import java.util.Random;

public class ExceptionGenerator {

    private final int exceptionProbability;
    private final Random random;

    public ExceptionGenerator(final int exceptionProbability) {
        this.exceptionProbability = exceptionProbability;
        this.random = new Random();
    }

    public void generateRandomException() {
        if (random.nextInt(100) < exceptionProbability) {
            throw new RandomGeneratorException();
        }
    }

}
