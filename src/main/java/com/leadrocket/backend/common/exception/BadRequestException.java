package com.leadrocket.backend.common.exception;

public class BadRequestException extends ApiException {
	public BadRequestException(String message) {
		super(message, 400);
	}
}
