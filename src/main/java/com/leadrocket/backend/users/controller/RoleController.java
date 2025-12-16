package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.repository.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private final RoleRepository repository;

	public RoleController(RoleRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Role> getAll() {
		return repository.findAll();
	}

	@PostMapping
	public Role create(@RequestBody Role role) {
		return repository.save(role);
	}
}

