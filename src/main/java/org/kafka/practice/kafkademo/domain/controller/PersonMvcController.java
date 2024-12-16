package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.PersonBusinessService;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.kafka.practice.kafkademo.domain.utils.WebPageModelUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PersonMvcController {

    private final PersonBusinessService personBusinessService;
    private final String personsListApiEndpointPath;
    private final int pageUpdateIntervalMs;
    private final int pageMaxElementsSize;

    @GetMapping("${web.pages.endpoints.persons-list-path}")
    public String personsList(@RequestParam(defaultValue = "0") final int page,
                              @RequestParam(defaultValue = "100") final int size,
                              final Model model) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        final var personsPage = personBusinessService.getPersons(PageRequest.of(page, size));
        WebPageModelUtils.addRequiredAttributes(model, personsListApiEndpointPath, personsPage, pageUpdateIntervalMs);
        return "personsList";
    }

}
