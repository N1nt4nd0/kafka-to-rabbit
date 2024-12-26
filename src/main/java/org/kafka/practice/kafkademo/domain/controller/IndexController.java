package org.kafka.practice.kafkademo.domain.controller;

import org.kafka.practice.kafkademo.domain.annotations.MvcExceptionHandling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@MvcExceptionHandling
public class IndexController {

    @GetMapping("/")
    public String index(final Model model) {
        if (model.containsAttribute("do-test-runtime-exception")) {
            throw new RuntimeException("Test runtime exception occurred");
        }
        return "index";
    }

}
