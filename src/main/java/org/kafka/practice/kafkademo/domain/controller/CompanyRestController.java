package org.kafka.practice.kafkademo.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company", description = "The companies rest controller")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyUseCases companyUseCases;
    private final WebPagesConfig webPagesConfig;

    @Operation(summary = "Get companies page")
    @GetMapping("${web.rest-api.endpoints.companies-list}")
    public ResponseEntity<Page<CompanyDtoOut>> companyPage(@RequestParam(defaultValue = "0") final int page,
                                                           @RequestParam(defaultValue = "50") final int size) {
        PageableUtils.checkSizeRange(size, webPagesConfig.getPageMaxElementsSize());
        final var companyPage = companyUseCases.getCompanies(PageRequest.of(page, size));
        return ResponseEntity.ok(companyPage);
    }

    @Operation(summary = "Fill random companies in database by specified data")
    @PostMapping("${web.rest-api.endpoints.companies-fill}")
    public ResponseEntity<FillRandomDataDtoOut> fillRandomCompanies(
            @RequestBody final FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn) {
        final var fillRandomDtoOut = companyUseCases.fillRandomCompanies(fillRandomCompaniesDtoIn);
        return ResponseEntity.ok(fillRandomDtoOut);
    }

}
