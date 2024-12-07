package org.kafka.practice.kafkademo.domain.utils;

import java.util.Random;

public class RandomErrorGenerator {

    private final Random random;
    private final int errorProbability;

    public RandomErrorGenerator(final int errorProbability) {
        this.errorProbability = errorProbability;
        this.random = new Random();
    }

    public void generate() {
        if (random.nextInt(100) < errorProbability) {
            throw new RuntimeException("Random error occurred");
        }
    }

}
