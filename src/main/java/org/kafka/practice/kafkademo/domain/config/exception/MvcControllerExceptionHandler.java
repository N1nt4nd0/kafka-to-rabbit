package org.kafka.practice.kafkademo.domain.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.kafka.practice.kafkademo.domain.controller.mvc.MvcControllerPackageMarker;
import org.kafka.practice.kafkademo.domain.utils.LogHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackageClasses = MvcControllerPackageMarker.class)
public class MvcControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception exception, final Model model) {
        model.addAttribute("error", String.valueOf(exception));
        LogHelper.logError("Controller error occurred", exception, log);
        return "error";
    }

}
