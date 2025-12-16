package com.leadrocket.backend.security.permission;

import org.springframework.stereotype.Service;

@Service("permissionCheckerService")
public class PermissionService {

	public boolean check(String userId, String permission) {
		return true;
	}
}
