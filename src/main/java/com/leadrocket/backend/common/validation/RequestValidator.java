// Simple validation helpers

package com.leadrocket.backend.common.validation;

import com.leadrocket.backend.common.exception.BadRequestException;

public final class RequestValidator {

	private RequestValidator() {}

	public static void notNull(Object value, String message) {
		if (value == null) {
			throw new BadRequestException(message);
		}
	}

	public static void notBlank(String value, String message) {
		if (value == null || value.trim().isEmpty()) {
			throw new BadRequestException(message);
		}
	}
}
