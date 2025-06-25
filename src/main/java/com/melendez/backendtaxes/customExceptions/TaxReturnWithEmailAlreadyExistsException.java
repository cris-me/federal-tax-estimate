package com.melendez.backendtaxes.customExceptions;

public class TaxReturnWithEmailAlreadyExistsException extends IllegalArgumentException {
    public TaxReturnWithEmailAlreadyExistsException(String message) {
        super(message);
    }
}
