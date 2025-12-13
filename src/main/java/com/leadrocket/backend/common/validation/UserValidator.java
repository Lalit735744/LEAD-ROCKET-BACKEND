package com.leadrocket.backend.common.validation;

import com.leadrocket.backend.common.exception.BadRequestException;

public class UserValidator {

	public static void validateUserId(String userId) {
		if (userId == null || userId.isBlank()) {
			throw new BadRequestException("Invalid user id");
		}
	}
}
