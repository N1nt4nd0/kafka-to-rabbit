package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.service.entities.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PersonsController {

    private final String personsListEndpointPath;
    private final int contentUpdateIntervalMs;
    private final PersonService personService;

    @GetMapping("${endpoints.persons-list-path}")
    public String personsList(@RequestParam(defaultValue = "0") final int page,
                              @RequestParam(defaultValue = "30") final int size,
                              final Model model) {
        final var pageable = PageRequest.of(page, size);
        final var personsPage = personService.getPersons(pageable);
        model.addAttribute("updateInterval", contentUpdateIntervalMs);
        model.addAttribute("contentPath", personsListEndpointPath);
        model.addAttribute("contentPage", personsPage);
        return "persons_list";
    }

}
