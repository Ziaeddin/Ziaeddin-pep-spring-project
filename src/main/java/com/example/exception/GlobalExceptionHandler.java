package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Centralizes exception handling across all controllers.
 * Provides consistent error response format for different types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles Spring's ResponseStatusException.
     * Uses the status and reason from the exception itself.
     * 
     * @param ex The ResponseStatusException that was thrown
     * @return ResponseEntity with error details and appropriate status code
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", ex.getStatus().toString());
        errorResponse.put("message", ex.getReason());
        
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
    
    /**
     * Handles username already exists exceptions.
     * Returns HTTP 409 Conflict with error details.
     * 
     * @param ex The UsernameAlreadyExistsException that was thrown
     * @return ResponseEntity with error details and CONFLICT status
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.CONFLICT.toString());
        errorResponse.put("message", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    /**
     * Handles invalid credentials exceptions.
     * Returns HTTP 401 Unauthorized with error details.
     * 
     * @param ex The InvalidCredentialsException that was thrown
     * @return ResponseEntity with error details and UNAUTHORIZED status
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.toString());
        errorResponse.put("message", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    
    /**
     * Handles invalid message exceptions.
     * Returns HTTP 400 Bad Request with error details.
     * 
     * @param ex The InvalidMessageException that was thrown
     * @return ResponseEntity with error details and BAD_REQUEST status
     */
    @ExceptionHandler(InvalidMessageException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMessage(InvalidMessageException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        errorResponse.put("message", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Handles invalid account exceptions.
     * Returns HTTP 400 Bad Request with error details.
     * 
     * @param ex The InvalidAccountException that was thrown
     * @return ResponseEntity with error details and BAD_REQUEST status
     */
    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<Map<String, String>> handleInvalidAccount(InvalidAccountException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        errorResponse.put("message", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Fallback handler for any unhandled exceptions.
     * Returns HTTP 500 Internal Server Error with error details.
     * Provides a generic error message along with the exception message.
     * 
     * @param ex The Exception that was thrown
     * @return ResponseEntity with error details and INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.put("message", "An unexpected error occurred: " + ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
} 