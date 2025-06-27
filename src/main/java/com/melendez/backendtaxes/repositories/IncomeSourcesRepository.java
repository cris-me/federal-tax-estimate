package com.melendez.backendtaxes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.melendez.backendtaxes.models.IncomeSource;

public interface IncomeSourcesRepository extends MongoRepository<IncomeSource, String> {
    List<IncomeSource> findByUserIdAndYear(String userId, int year);
}
