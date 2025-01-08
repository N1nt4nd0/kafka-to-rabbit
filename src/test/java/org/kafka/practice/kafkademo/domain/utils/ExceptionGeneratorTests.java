package org.kafka.practice.kafkademo.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.exception.RandomGeneratorException;

import java.util.stream.IntStream;

public class ExceptionGeneratorTests {

    @Test
    void testExceptionGeneratorWithOneHundredProbabilityThrowRandomGeneratorExceptionOneHundredTimes() {
        final var sut = new ExceptionGenerator(100);
        Assertions.assertAll("RandomGeneratorException throw 100 times",
                IntStream.range(0, 100).mapToObj(i -> () ->
                        Assertions.assertThrows(RandomGeneratorException.class, sut::generateRandomException)));
    }

    @Test
    void testExceptionGeneratorWithZeroProbabilityNotThrowRandomGeneratorExceptionOneHundredTimes() {
        final var sut = new ExceptionGenerator(0);
        Assertions.assertAll("RandomGeneratorException not even thrown 100 times",
                IntStream.range(0, 100).mapToObj(i -> () ->
                        Assertions.assertDoesNotThrow(sut::generateRandomException)));
    }

}