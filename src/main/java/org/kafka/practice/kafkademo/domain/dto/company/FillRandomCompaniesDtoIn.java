package org.kafka.practice.kafkademo.domain.dto.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FillRandomCompaniesDtoIn {

    private final int companiesCount;

}
