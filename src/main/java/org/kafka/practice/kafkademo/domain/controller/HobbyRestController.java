package org.kafka.practice.kafkademo.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.annotations.RestExceptionHandling;
import org.kafka.practice.kafkademo.domain.business.service.HobbyUseCases;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.HobbyDtoOut;
import org.kafka.practice.kafkademo.domain.dto.TruncateTableDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
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
@RestExceptionHandling
@RequiredArgsConstructor
public class HobbyRestController {

    private final HobbyUseCases hobbyUseCases;
    private final WebPagesConfig webPagesConfig;

    @Operation(summary = "Get hobbies page")
    @GetMapping("${web.rest-api.endpoints.hobby-list}")
    public ResponseEntity<Page<HobbyDtoOut>> hobbyPage(@RequestParam(defaultValue = "0") final int page,
                                                       @RequestParam(defaultValue = "50") final int size) {
        PageableUtils.checkSizeRange(size, webPagesConfig.getPageMaxElementsSize());
        return ResponseEntity.ok(hobbyUseCases.getHobbies(PageRequest.of(page, size)));
    }

    @Operation(summary = "Fill random hobbies")
    @PostMapping("${web.rest-api.endpoints.hobby-fill}")
    public ResponseEntity<FillRandomDataDtoOut> fillRandomHobbies(
            @RequestBody final FillRandomHobbiesDtoIn fillRandomCompaniesDtoIn) {
        return ResponseEntity.ok(hobbyUseCases.fillRandomHobbies(fillRandomCompaniesDtoIn));
    }

    @Operation(summary = "Truncate hobbies table")
    @PostMapping("${web.rest-api.endpoints.hobby-truncate}")
    public ResponseEntity<TruncateTableDtoOut> truncateHobbies() {
        return ResponseEntity.ok(hobbyUseCases.truncateHobbies());
    }

}
