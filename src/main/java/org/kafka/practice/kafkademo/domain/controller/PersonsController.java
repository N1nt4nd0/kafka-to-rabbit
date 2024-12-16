package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.exception.PageSizeLimitException;
import org.kafka.practice.kafkademo.domain.business.service.PersonBusinessService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PersonsController {

    private final PersonBusinessService personBusinessService;
    private final String personsListEndpointPath;
    private final int pageUpdateIntervalMs;
    private final int pageMaxElementsSize;

    @GetMapping("${web.endpoints.persons-list-path}")
    public String personsList(@RequestParam(defaultValue = "0") final int page,
                              @RequestParam(defaultValue = "15") final int size,
                              final Model model) {
        if (size > pageMaxElementsSize) {
            throw new PageSizeLimitException(pageMaxElementsSize);
        }
        final var pageable = PageRequest.of(page, size);
        final var personsPage = personBusinessService.getPersons(pageable);
        model.addAttribute("updateInterval", pageUpdateIntervalMs);
        model.addAttribute("contentPath", personsListEndpointPath);
        model.addAttribute("contentPage", personsPage);
        return "personsList";
    }

}
