package org.kafka.practice.kafkademo.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.config.WebPagesConfig;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
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

@Tag(name = "Person", description = "The persons rest controller")
@RestController
@RequiredArgsConstructor
public class PersonRestController {

    private final PersonUseCases personUseCases;
    private final WebPagesConfig webPagesConfig;

    @Operation(summary = "Get persons page")
    @GetMapping("${web.rest-api.endpoints.persons-list}")
    public ResponseEntity<Page<PersonDtoOut>> personPage(@RequestParam(defaultValue = "0") final int page,
                                                         @RequestParam(defaultValue = "50") final int size) {
        PageableUtils.checkSizeRange(size, webPagesConfig.getPageMaxElementsSize());
        final var personPage = personUseCases.getPersons(PageRequest.of(page, size));
        return ResponseEntity.ok(personPage);
    }

    @Operation(summary = "Fill random persons in database by specified data")
    @PostMapping("${web.rest-api.endpoints.persons-fill}")
    public ResponseEntity<FillRandomDataDtoOut> fillRandomPersons(
            @RequestBody final FillRandomPersonsDtoIn fillRandomPersonsDtoIn) {
        final var fillRandomDtoOut = personUseCases.fillRandomPersons(fillRandomPersonsDtoIn);
        return ResponseEntity.ok(fillRandomDtoOut);
    }


}
