package org.kafka.practice.kafkademo.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.PersonUseCases;
import org.kafka.practice.kafkademo.domain.dto.PersonDtoOut;
import org.kafka.practice.kafkademo.domain.utils.PageableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Person", description = "The persons rest controller")
@RestController
@RequiredArgsConstructor
public class PersonRestController {

    private final PersonUseCases personUseCases;
    private final int pageMaxElementsSize;

    @Operation(summary = "Get persons page")
    @GetMapping("${web.rest-api.endpoints.persons-list-api}")
    public ResponseEntity<Page<PersonDtoOut>> personsPage(@RequestParam(defaultValue = "0") final int page,
                                                          @RequestParam(defaultValue = "100") final int size) {
        PageableUtils.checkSizeRange(size, pageMaxElementsSize);
        final var personsPage = personUseCases.getPersons(PageRequest.of(page, size));
        return ResponseEntity.ok(personsPage);
    }

}
