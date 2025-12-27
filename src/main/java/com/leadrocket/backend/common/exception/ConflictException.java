package com.leadrocket.backend.common.exception;

public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(message, 409);
    }
}
