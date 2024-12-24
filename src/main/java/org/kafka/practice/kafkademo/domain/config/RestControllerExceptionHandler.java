package org.kafka.practice.kafkademo.domain.config;

import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.annotations.RestExceptionHandling;
import org.kafka.practice.kafkademo.domain.dto.ExceptionDtoOut;
import org.kafka.practice.kafkademo.domain.utils.LogHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(annotations = RestExceptionHandling.class)
public class RestControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDtoOut> handleException(Exception exception) {
        LogHelper.logError("RestController error occurred", exception, log);
        return ResponseEntity.internalServerError().body(new ExceptionDtoOut(exception.getMessage()));
    }

}
