package com.melendez.backendtaxes.customExceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class TaxServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("An error occured due to", ex.getMessage(), ex.fillInStackTrace());
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).build();
    }

    @ExceptionHandler(value = TaxReturnWithEmailDoesNotExistException.class)
    public ResponseEntity<Object> handleEmailDoesNotExistException(TaxReturnWithEmailDoesNotExistException ex){
        log.error("Error occured due to", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).build();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
        log.error("Error occured due to", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).build();
    }
}
