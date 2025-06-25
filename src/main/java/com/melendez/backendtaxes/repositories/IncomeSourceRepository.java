package com.melendez.backendtaxes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.melendez.backendtaxes.models.TaxReturn;

public interface IncomeSourceRepository extends MongoRepository<TaxReturn, String>{
    
}
