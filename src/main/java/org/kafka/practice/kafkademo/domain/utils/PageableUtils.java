package org.kafka.practice.kafkademo.domain.utils;

import org.kafka.practice.kafkademo.domain.exception.PageSizeLimitException;

public class PageableUtils {

    public static void checkSizeRange(final int size, final int max) {
        if (size > max) {
            throw new PageSizeLimitException(max);
        }
    }

}
