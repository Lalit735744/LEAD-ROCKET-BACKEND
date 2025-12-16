package com.leadrocket.backend.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

	private final JwtProvider provider;

	public JwtValidator(JwtProvider provider) {
		this.provider = provider;
	}

	public String validateAndGetUser(String token) {
		return provider.getSubject(token);
	}
}
