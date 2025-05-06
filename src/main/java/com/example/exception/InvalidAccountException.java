package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an account fails validation or cannot be processed.
 * Returns HTTP 400 Bad Request when this exception is thrown.
 * Used for invalid account data during registration or other account operations.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccountException extends RuntimeException {
    /**
     * Constructs a new InvalidAccountException with the specified error message.
     * 
     * @param message The error message explaining why the account is invalid
     */
    public InvalidAccountException(String message) {
        super(message);
    }
} 