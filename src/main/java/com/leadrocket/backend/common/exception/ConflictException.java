package com.leadrocket.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a requested operation conflicts with current state (e.g., duplicate email).
 * Results in an HTTP 409 response.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}
