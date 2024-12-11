package org.kafka.practice.kafkademo.domain.exception;

public class PageSizeLimitException extends IllegalArgumentException {

    public PageSizeLimitException(final int maxPageSize) {
        super("Page size must be less than " + maxPageSize);
    }

}
