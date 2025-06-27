package com.melendez.backendtaxes.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.melendez.backendtaxes.customExceptions.TaxReturnWithEmailDoesNotExistException;
import com.melendez.backendtaxes.customExceptions.TaxServiceExceptionHandler;
import com.melendez.backendtaxes.customExceptions.UserNotFoundException;

@SpringBootTest
@ContextConfiguration(classes = { TaxServiceExceptionHandler.class })
public class ExceptionHandlersTest {
    @Autowired
    private TaxServiceExceptionHandler exceptionHandler;

    @Test
    void testHandleException() {
        ResponseEntity<Object> responseEntity = exceptionHandler.handleException(new Exception("Test Exception"));
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode()));
    }

    @Test
    void testHandleEmailDoesNotExistException() {
        ResponseEntity<Object> responseEntity = exceptionHandler
                .handleEmailDoesNotExistException(new TaxReturnWithEmailDoesNotExistException("Test Exception"));
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode()));
    }

    @Test
    void testHandleUserNotFoundException() {
        ResponseEntity<Object> responseEntity = exceptionHandler
                .handleUserNotFoundException(new UserNotFoundException("Test Exception"));
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode()));
    }
}
