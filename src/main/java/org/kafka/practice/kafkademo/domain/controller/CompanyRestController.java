package org.kafka.practice.kafkademo.domain.controller;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.CompanyBusinessService;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyBusinessService companyBusinessService;
    private final int pageMaxElementsSize;

    @GetMapping("${web.rest-api.endpoints.companies-list-api}")
    public ResponseEntity<Page<CompanyDtoOut>> companiesPage(@RequestParam(defaultValue = "0") final int page,
                                                             @RequestParam(defaultValue = "100") final int size) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        final var companiesPage = companyBusinessService.getCompanies(PageRequest.of(page, size));
        return ResponseEntity.ok(companiesPage);
    }

}
