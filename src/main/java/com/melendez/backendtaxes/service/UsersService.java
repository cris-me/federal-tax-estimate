package com.melendez.backendtaxes.service;

import java.util.Optional;

import com.melendez.backendtaxes.models.User;

public interface UsersService {
    public Optional<User> getOneUser(String email);

    public User createUser(User user);

    public boolean deleteUser(String id);
}
