package com.melendez.backendtaxes.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.melendez.backendtaxes.models.User;
import com.melendez.backendtaxes.service.UsersService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsersService usersService;

    @Test
    void testGetOneUserByPath() throws Exception {
        User mockUser = new User();
        when(usersService.getOneUser(anyString())).thenReturn(Optional.of(mockUser));
        String email = "test@example.com";
        mockMvc.perform(get("/api/users/{email}", email))
                .andExpect(status().isOk());
        verify(usersService, times(1)).getOneUser(email);
    }
}
