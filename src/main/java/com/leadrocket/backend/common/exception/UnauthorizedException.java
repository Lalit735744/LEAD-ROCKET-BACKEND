package com.leadrocket.backend.common.exception;

/**
 * Thrown when request is unauthorized (missing/invalid auth key or token).
 * Typically translated to HTTP 401 by a global exception handler.
 */
public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(String message) {
		super(message);
	}
}
