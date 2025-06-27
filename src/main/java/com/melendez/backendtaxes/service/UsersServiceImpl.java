package com.melendez.backendtaxes.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.melendez.backendtaxes.models.User;
import com.melendez.backendtaxes.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getOneUser(String email) {
        log.info("Gathering data for one user");
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        User saved = userRepository.save(user);
        return saved;
    }

    // return true if deletion successful
    @Override
    public boolean deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
