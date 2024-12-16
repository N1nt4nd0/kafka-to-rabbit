package org.kafka.practice.kafkademo.domain.exception;

import lombok.NonNull;

public class JobNotFoundByTitleException extends RuntimeException {

    public JobNotFoundByTitleException(@NonNull final String jobTitle) {
        super("Can't find job by title: " + jobTitle);
    }

}
