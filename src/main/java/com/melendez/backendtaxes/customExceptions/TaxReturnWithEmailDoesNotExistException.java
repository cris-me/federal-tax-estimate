package com.melendez.backendtaxes.customExceptions;

public class TaxReturnWithEmailDoesNotExistException extends IllegalArgumentException {
    public TaxReturnWithEmailDoesNotExistException(String message) {
        super(message);
    }
}
