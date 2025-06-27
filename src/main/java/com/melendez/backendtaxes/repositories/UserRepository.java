package com.melendez.backendtaxes.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.melendez.backendtaxes.models.User;

public interface UserRepository extends MongoRepository<User, String>{
    // Return one return that matches the email
    Optional<User> findByEmail(String email);
}
