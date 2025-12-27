package com.leadrocket.backend.common.exception;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(message, 403);
    }
}
