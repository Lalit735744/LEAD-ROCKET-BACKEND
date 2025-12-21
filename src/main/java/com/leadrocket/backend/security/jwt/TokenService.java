package com.leadrocket.backend.security.jwt;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

	private final JwtProvider provider;

	public TokenService(JwtProvider provider) {
		this.provider = provider;
	}

	public String generateAccessToken(String userId) {
		return provider.generateAccessToken(userId);
	}

	public String generateAccessToken(String userId, String companyId) {
		return provider.generateAccessToken(userId, companyId);
	}

	public String generateRefreshToken(String userId) {
		return provider.generateRefreshToken(userId);
	}
}
