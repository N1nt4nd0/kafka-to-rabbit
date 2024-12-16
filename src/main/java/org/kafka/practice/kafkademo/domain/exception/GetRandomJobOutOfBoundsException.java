package org.kafka.practice.kafkademo.domain.exception;

public class GetRandomJobOutOfBoundsException extends RuntimeException {

    public GetRandomJobOutOfBoundsException(final int bound, final long jobsCount) {
        super(String.format("Jobs count in database less than bound. Bound: %s, Jobs count: %s", bound, jobsCount));
    }

}
