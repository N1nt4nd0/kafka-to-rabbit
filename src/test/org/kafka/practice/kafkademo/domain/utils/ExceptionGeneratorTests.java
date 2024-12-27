package org.kafka.practice.kafkademo.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;
import org.kafka.practice.kafkademo.domain.generation.ExceptionGenerator;

import java.util.stream.IntStream;

public class ExceptionGeneratorTests {

    private final ExceptionGenerator sut = new ExceptionGenerator(100);

    @Test
    void testExceptionGeneratorWithOneHundredProbabilityThrowRandomGeneratorExceptionOneHundredTimes() {
        Assertions.assertAll("RandomGeneratorException throw 100 times",
                IntStream.range(0, 100).mapToObj(i -> () ->
                        Assertions.assertThrows(RandomGeneratorException.class, sut::generateRandomException))
        );
    }

}