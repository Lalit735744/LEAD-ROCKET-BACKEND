// Base API exception

package com.leadrocket.backend.common.exception;

public abstract class ApiException extends RuntimeException {

    private final int status;

    protected ApiException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
