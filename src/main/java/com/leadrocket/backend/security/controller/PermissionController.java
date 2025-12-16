package com.leadrocket.backend.security.controller;

import com.leadrocket.backend.users.model.Permission;
import com.leadrocket.backend.security.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

	private final PermissionService service;

	public PermissionController(PermissionService service) {
		this.service = service;
	}

	@GetMapping
	public List<Permission> getAll() {
		return service.getAll();
	}

	@PostMapping
	public Permission create(@RequestBody Permission permission) {
		return service.create(permission);
	}
}

