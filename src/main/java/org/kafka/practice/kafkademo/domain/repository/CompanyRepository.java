package org.kafka.practice.kafkademo.domain.repository;

import org.bson.types.ObjectId;
import org.kafka.practice.kafkademo.domain.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, ObjectId> {

    Optional<Company> findByCompanyName(String companyName);

}
