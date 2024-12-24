package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.annotations.MvcExceptionHandling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@MvcExceptionHandling
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String index(final Model model) {
        return "index";
    }

}
