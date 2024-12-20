package org.kafka.practice.kafkademo.domain.business.service;

import org.kafka.practice.kafkademo.domain.dto.common.FillRandomAllDataDtoIn;
import org.kafka.practice.kafkademo.domain.dto.common.FillRandomAllDataDtoOut;

public interface CommonUseCases {

    FillRandomAllDataDtoOut fillRandomAllData(FillRandomAllDataDtoIn fillRandomAllDataDtoIn);

}
