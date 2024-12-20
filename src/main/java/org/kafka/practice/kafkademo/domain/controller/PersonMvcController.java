package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.kafka.practice.kafkademo.domain.utils.WebPageModelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PersonMvcController {

    private final String personsListApiPath;
    private final int pageUpdateIntervalMs;
    private final int pageMaxElementsSize;

    @GetMapping("${web.pages.endpoints.persons-list-path}")
    public String personsList(@RequestParam(defaultValue = "0") final int page,
                              @RequestParam(defaultValue = "50") final int size,
                              final Model model) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        WebPageModelUtils.addRequiredAttributes(model, personsListApiPath, page, size, pageUpdateIntervalMs);
        return "personList";
    }

}
