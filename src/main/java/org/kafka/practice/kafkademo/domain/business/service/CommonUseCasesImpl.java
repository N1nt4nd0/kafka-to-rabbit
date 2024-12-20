package org.kafka.practice.kafkademo.domain.business.service;

import lombok.RequiredArgsConstructor;
import org.kafka.practice.kafkademo.domain.dto.common.FillRandomAllDataDtoIn;
import org.kafka.practice.kafkademo.domain.dto.common.FillRandomAllDataDtoOut;
import org.kafka.practice.kafkademo.domain.dto.company.FillRandomCompaniesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.hobby.FillRandomHobbiesDtoIn;
import org.kafka.practice.kafkademo.domain.dto.person.FillRandomPersonsDtoIn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommonUseCasesImpl implements CommonUseCases {

    private final CompanyUseCases companyUseCases;
    private final PersonUseCases personUseCases;
    private final HobbyUseCases hobbyUseCases;

    @Override
    @Transactional
    public FillRandomAllDataDtoOut fillRandomAllData(final FillRandomAllDataDtoIn fillRandomAllDataDtoIn) {
        final var companyCount = companyUseCases.getCompanyCount().getCompanyCount();
        final var hobbyCount = hobbyUseCases.getHobbyCount().getHobbyCount();
        long filledCompanyCount = 0L, filledHobbyCount = 0L;
        if (companyCount == 0) {
            filledCompanyCount = companyUseCases
                    .fillRandomCompanies(new FillRandomCompaniesDtoIn(fillRandomAllDataDtoIn.getInitCompaniesCount()))
                    .getFilledCount();
        }
        if (hobbyCount == 0) {
            filledHobbyCount = hobbyUseCases
                    .fillRandomHobbies(new FillRandomHobbiesDtoIn(fillRandomAllDataDtoIn.getInitHobbiesCount()))
                    .getFilledCount();
        }
        var filledPersonCount = personUseCases.
                fillRandomPersons(new FillRandomPersonsDtoIn(fillRandomAllDataDtoIn.getPersonsCount(),
                        fillRandomAllDataDtoIn.getPersonHobbiesMaxCount()))
                .getFilledCount();
        return new FillRandomAllDataDtoOut(filledPersonCount, filledCompanyCount, filledHobbyCount);
    }

}
