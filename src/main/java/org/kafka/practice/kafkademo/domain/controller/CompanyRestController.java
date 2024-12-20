package org.kafka.practice.kafkademo.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company", description = "The companies rest controller")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyUseCases companyUseCases;
    private final int pageMaxElementsSize;

    @Operation(summary = "Get companies page")
    @GetMapping("${web.rest-api.endpoints.companies-list-api}")
    public ResponseEntity<Page<CompanyDtoOut>> companiesPage(@RequestParam(defaultValue = "0") final int page,
                                                             @RequestParam(defaultValue = "50") final int size) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        final var companyPage = companyUseCases.getCompanies(PageRequest.of(page, size));
        return ResponseEntity.ok(companyPage);
    }

}
