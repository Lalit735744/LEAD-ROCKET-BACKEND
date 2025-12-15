package com.leadrocket.backend.security.jwt;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private final JwtProvider provider;

	public TokenService(JwtProvider provider) {
		this.provider = provider;
	}

	public String generateToken(String userId) {
		return provider.generate(userId);
	}
}
