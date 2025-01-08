package org.kafka.practice.kafkademo.domain.repository;

import org.kafka.practice.kafkademo.domain.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, Long> {

    Optional<Company> findByCompanyName(String companyName);

}
