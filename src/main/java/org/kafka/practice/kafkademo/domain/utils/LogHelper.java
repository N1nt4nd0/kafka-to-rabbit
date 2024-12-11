package org.kafka.practice.kafkademo.domain.utils;

import lombok.NonNull;
import org.slf4j.Logger;

public class LogHelper {

    public static void logError(@NonNull final String message,
                                @NonNull final Throwable throwable,
                                @NonNull final Logger logger) {
        logger.error("{}: {}. Cause: {}", message, throwable, String.valueOf(throwable.getCause()));
        logger.trace("Error details:", throwable);
    }

}
