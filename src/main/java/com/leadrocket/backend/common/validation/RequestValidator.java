package com.leadrocket.backend.common.validation;

import com.leadrocket.backend.common.exception.BadRequestException;

public class RequestValidator {

	public static void notNull(Object obj, String message) {
		if (obj == null) {
			throw new BadRequestException(message);
		}
	}
}
