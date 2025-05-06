package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to register an account with a username that already exists.
 * Returns HTTP 409 Conflict when this exception is thrown.
 * Used during account registration to prevent duplicate usernames.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UsernameAlreadyExistsException with the specified error message.
     * 
     * @param message The error message explaining the username conflict
     */
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
} 