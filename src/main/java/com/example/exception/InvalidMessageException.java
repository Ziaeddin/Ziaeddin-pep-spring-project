package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a message fails validation or cannot be processed.
 * Returns HTTP 400 Bad Request when this exception is thrown.
 * Used for invalid message content, failed updates, or other message-related errors.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMessageException extends RuntimeException {
    /**
     * Constructs a new InvalidMessageException with the specified error message.
     * 
     * @param message The error message explaining why the message is invalid
     */
    public InvalidMessageException(String message) {
        super(message);
    }
} 