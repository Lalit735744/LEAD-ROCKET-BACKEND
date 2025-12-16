package com.leadrocket.backend.security.service;

import com.leadrocket.backend.users.model.Permission;
import com.leadrocket.backend.security.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

	private final PermissionRepository repository;

	public PermissionService(PermissionRepository repository) {
		this.repository = repository;
	}

	public List<Permission> getAll() {
		return repository.findAll();
	}

	public Permission create(Permission permission) {
		return repository.save(permission);
	}
}

