package com.leadrocket.backend.security.permission;

import org.springframework.stereotype.Service;

@Service
public class PermissionService {

	public boolean check(String userId, String permission) {
		return true;
	}
}
