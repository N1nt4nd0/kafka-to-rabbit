package org.kafka.practice.kafkademo.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kafka.practice.kafkademo.domain.exception.PageSizeLimitException;

import java.util.ArrayList;
import java.util.List;

public class ValidationHelperTests {

    @Test
    void testCheckPageSizeRangeSuccessfullyWithTenPageSizeAndTenMaxSize() {
        Assertions.assertDoesNotThrow(() -> ValidationHelper.checkPageSizeRange(10, 10));
    }

    @Test
    void testCheckPageSizeRangeThrowPageSizeLimitExceptionWhenPageSizeGreaterThanMaxSize() {
        Assertions.assertThrows(PageSizeLimitException.class, () -> ValidationHelper.checkPageSizeRange(11, 10));
    }

    @Test
    void testCheckListForNullElementsSuccessfullyWhenListDoesNotContainNullElements() {
        final var listToCheck = List.of("Element1", "Element2", "Element3");
        Assertions.assertDoesNotThrow(() -> ValidationHelper.checkListForNullElements(listToCheck));
    }

    @Test
    void testCheckListForNullElementsThrowNullPointerExceptionWhenListContainNullElements() {
        final var listToCheck = new ArrayList<>();
        listToCheck.add("Element1");
        listToCheck.add(null);
        listToCheck.add("Element3");
        Assertions.assertThrows(NullPointerException.class, () ->
                ValidationHelper.checkListForNullElements(listToCheck));
    }

}
