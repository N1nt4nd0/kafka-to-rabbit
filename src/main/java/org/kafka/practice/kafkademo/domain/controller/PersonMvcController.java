package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.config.EndpointsConfig;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.kafka.practice.kafkademo.domain.utils.WebPageModelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PersonMvcController {

    private final EndpointsConfig endpointsConfig;
    private final WebPagesConfig webPagesConfig;

    @GetMapping("${web.pages.endpoints.persons-list}")
    public String personList(@RequestParam(defaultValue = "0") final int page,
                             @RequestParam(defaultValue = "50") final int size,
                             final Model model) {
        PageableUtils.checkSizeRange(size, webPagesConfig.getPageMaxElementsSize());
        WebPageModelUtils.addRequiredAttributes(model, endpointsConfig, webPagesConfig, page, size);
        return "personList";
    }

}
