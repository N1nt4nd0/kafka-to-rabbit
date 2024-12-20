package org.kafka.practice.kafkademo.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.business.service.HobbyUseCases;
import org.kafka.practice.kafkademo.domain.dto.FillRandomDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hobby", description = "The companies rest controller")
@RestController
@RequiredArgsConstructor
public class HobbyRestController {

    private final HobbyUseCases hobbyUseCases;

    @Operation(summary = "Fill random hobbies in database by specified data")
    @PostMapping("${web.rest-api.endpoints.hobbies-fill}")
    public ResponseEntity<FillRandomDataDtoOut> fillRandomCompanies(
            @RequestBody final FillRandomHobbiesDtoIn fillRandomHobbiesDtoIn) {
        final var fillRandomDtoOut = hobbyUseCases.fillRandomHobbies(fillRandomHobbiesDtoIn);
        return ResponseEntity.ok(fillRandomDtoOut);
    }

}
