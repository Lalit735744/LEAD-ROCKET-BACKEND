// Represents authenticated request context (JWT-derived)

package com.leadrocket.backend.security.authkey;

public class AuthSession {

	private final String userId;
	private final String companyId;

	public AuthSession(String userId, String companyId) {
		this.userId = userId;
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public String getCompanyId() {
		return companyId;
	}
}
