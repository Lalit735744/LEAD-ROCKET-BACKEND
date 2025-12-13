package com.leadrocket.backend.security.authkey;

import com.leadrocket.backend.common.exception.UnauthorizedException;

public class AuthKeyValidator {

	public static void validate(String authKey) {
		if (authKey == null || authKey.isBlank()) {
			throw new UnauthorizedException("Invalid Auth Key");
		}
	}
}
