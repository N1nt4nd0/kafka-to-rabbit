package org.kafka.practice.kafkademo.domain.utils;

import lombok.NonNull;
import org.slf4j.Logger;

public class LogHelper {

    public static void logError(@NonNull final String prefix,
                                @NonNull final Throwable root,
                                @NonNull final Logger logger) {
        final var cause = root.getCause() == null ? root : root.getCause();
        logger.error("{} error occurred: {}({})", prefix, cause.getClass().getName(), cause.getMessage());
        logger.trace("Error details:", cause);
    }

}
