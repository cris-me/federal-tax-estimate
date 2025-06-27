package com.melendez.backendtaxes.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.melendez.backendtaxes.models.TaxReturn;

public interface TaxEstimateRepository extends MongoRepository<TaxReturn, String> {
    Optional<TaxReturn> findByUserIdAndYear(String userId, int year);
}
