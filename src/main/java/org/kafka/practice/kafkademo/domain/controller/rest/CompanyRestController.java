package org.kafka.practice.kafkademo.domain.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.CompanyUseCases;
import org.kafka.practice.kafkademo.domain.config.web.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoIn;
import org.kafka.practice.kafkademo.domain.dto.company.CompanyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.utils.ValidationHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company", description = "The company rest controller")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyUseCases companyUseCases;
    private final WebPagesConfig webPagesConfig;

    @Operation(summary = "Get companies page")
    @GetMapping("${web.rest-api.endpoints.company-list}")
    public ResponseEntity<Page<CompanyDtoOut>> companyPage(@RequestParam(defaultValue = "0") final int page,
                                                           @RequestParam(defaultValue = "50") final int size) {
        ValidationHelper.checkPageSizeRange(size, webPagesConfig.getPageMaxElementsSize());
        return ResponseEntity.ok(companyUseCases.getCompanies(PageRequest.of(page, size)));
    }

    @Operation(summary = "Fill random companies")
    @PostMapping("${web.rest-api.endpoints.company-fill}")
    public ResponseEntity<FillRandomDataDtoOut> fillRandomCompanies(
            @Valid @RequestBody final FillRandomCompaniesDtoIn fillRandomCompaniesDtoIn) {
        return ResponseEntity.ok(companyUseCases.fillRandomCompanies(fillRandomCompaniesDtoIn));
    }

    @Operation(summary = "Create new company")
    @PostMapping("${web.rest-api.endpoints.company-create}")
    public ResponseEntity<CompanyDtoOut> createCompany(@Valid @RequestBody final CompanyDtoIn companyDtoIn) {
        return ResponseEntity.ok(companyUseCases.createCompany(companyDtoIn));
    }

    @Operation(summary = "Truncate companies table")
    @PostMapping("${web.rest-api.endpoints.company-truncate}")
    public ResponseEntity<TruncateTableDtoOut> truncateHobbies() {
        return ResponseEntity.ok(companyUseCases.truncateCompanies());
    }

}
