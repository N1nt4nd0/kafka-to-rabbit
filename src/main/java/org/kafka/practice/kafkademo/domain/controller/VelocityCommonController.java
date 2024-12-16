package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.CompanyBusinessService;
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
public class VelocityCommonController {

    private final CompanyBusinessService companyBusinessService;
    private final PersonBusinessService personBusinessService;
    private final String companiesListEndpointPath;
    private final String personsListEndpointPath;
    private final int pageUpdateIntervalMs;
    private final int pageMaxElementsSize;

    @GetMapping("${web.endpoints.persons-list-path}")
    public String personsList(@RequestParam(defaultValue = "0") final int page,
                              @RequestParam(defaultValue = "100") final int size,
                              final Model model) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        final var personsPage = personBusinessService.getPersons(PageRequest.of(page, size));
        WebPageModelUtils.addRequiredAttributes(model, personsListEndpointPath, personsPage, pageUpdateIntervalMs);
        return "personsList";
    }

    @GetMapping("${web.endpoints.companies-list-path}")
    public String companiesList(@RequestParam(defaultValue = "0") final int page,
                                @RequestParam(defaultValue = "100") final int size,
                                final Model model) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        final var companiesPage = companyBusinessService.getCompanies(PageRequest.of(page, size));
        WebPageModelUtils.addRequiredAttributes(model, companiesListEndpointPath, companiesPage, pageUpdateIntervalMs);
        return "companiesList";
    }


}
