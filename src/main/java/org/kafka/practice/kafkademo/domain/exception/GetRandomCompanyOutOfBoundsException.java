package org.kafka.practice.kafkademo.domain.exception;

public class GetRandomCompanyOutOfBoundsException extends RuntimeException {

    public GetRandomCompanyOutOfBoundsException(final int bound, final long companiesCount) {
        super(String.format("Companies count in database less than bound. Bound: %s, Companies count: %s",
                bound, companiesCount));
    }

}
