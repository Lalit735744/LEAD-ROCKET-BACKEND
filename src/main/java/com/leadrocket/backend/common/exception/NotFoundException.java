package com.leadrocket.backend.common.exception;

public class NotFoundException extends ApiException {
	public NotFoundException(String message) {
		super(message, 404);
	}
}
