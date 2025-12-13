package com.leadrocket.backend.security.authkey;

import org.springframework.stereotype.Service;

@Service
public class AuthKeyService {

	public boolean isValid(String authKey) {
		return authKey != null && !authKey.isBlank();
	}
}
