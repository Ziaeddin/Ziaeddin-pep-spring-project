package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when authentication fails due to invalid credentials.
 * Returns HTTP 401 Unauthorized when this exception is thrown.
 * Used during login attempts with incorrect username/password combinations.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException {
    /**
     * Constructs a new InvalidCredentialsException with the specified error message.
     * 
     * @param message The error message explaining the authentication failure
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
} 