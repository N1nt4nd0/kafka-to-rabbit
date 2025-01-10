package org.kafka.practice.kafkademo.domain.utils;

import lombok.NonNull;
import org.kafka.practice.kafkademo.domain.exception.PageSizeLimitException;

import java.util.List;
import java.util.Objects;

public class ValidationHelper {

    public static void checkPageSizeRange(final int size, final int max) {
        if (size > max) {
            throw new PageSizeLimitException(max);
        }
    }

    public static <E> void checkListForNullElements(@NonNull final List<E> list) {
        list.forEach(Objects::requireNonNull);
    }

}
