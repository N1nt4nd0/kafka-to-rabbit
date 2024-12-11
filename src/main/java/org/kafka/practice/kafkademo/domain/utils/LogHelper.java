package org.kafka.practice.kafkademo.domain.utils;

import lombok.NonNull;
import org.slf4j.Logger;

public class LogHelper {

    public static void logError(@NonNull final String prefix,
                                @NonNull final Throwable root,
                                @NonNull final Logger logger) {
        var cause = root.getCause();
        if (cause == null) {
            cause = root;
        }
        logger.error("{} error occurred: {}. Message: {}", prefix, cause.getClass().getName(), cause.getMessage());
        logger.trace("Error details:", cause);
    }

}
