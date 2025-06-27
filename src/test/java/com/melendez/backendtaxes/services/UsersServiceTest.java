package com.melendez.backendtaxes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.melendez.backendtaxes.models.User;
import com.melendez.backendtaxes.repositories.UserRepository;
import com.melendez.backendtaxes.service.UsersService;
import com.melendez.backendtaxes.service.UsersServiceImpl;

@SpringBootTest(classes = UsersServiceImpl.class)
public class UsersServiceTest {

    @Autowired
    private UsersService service;

    @MockitoBean
    private UserRepository repository;

    @Test
    void testGetOneUser() {
        User mockUser = new User();
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        User realUser = service.getOneUser("test@gmail.com").get();
        assertEquals(mockUser, realUser);
    }

    @Test
    void testCreateUser() {
        User mockUser = new User();
        when(repository.save(any())).thenReturn(mockUser);
        User realUser = service.createUser(new User());
        assertEquals(mockUser, realUser);
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void testDeleteUser(boolean value) {
        when(service.deleteUser(anyString())).thenReturn(value);
        boolean result = service.deleteUser("testId");
        assertEquals(value, result);
    }
}
