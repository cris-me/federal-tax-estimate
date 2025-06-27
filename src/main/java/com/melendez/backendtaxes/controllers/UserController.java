package com.melendez.backendtaxes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melendez.backendtaxes.models.User;
import com.melendez.backendtaxes.service.UsersService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UsersService usersService;

    @GetMapping("/{email}")
    public ResponseEntity<User> getOneUserByPath(@PathVariable String email) {
        return usersService.getOneUser(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
