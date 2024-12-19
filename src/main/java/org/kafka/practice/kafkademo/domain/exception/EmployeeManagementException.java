package org.kafka.practice.kafkademo.domain.exception;

public class EmployeeManagementException extends RuntimeException {

    public EmployeeManagementException(final String message) {
        super(message);
    }

}
