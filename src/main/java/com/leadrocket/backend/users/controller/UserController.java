package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.dto.UserRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * REST controller exposing user related APIs.
 *
 * Endpoints:
 * - POST /api/users : register a new user (public)
 * - GET  /api/users : list users (public)
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	/**
	 * Create a new user.
	 * Expects a JSON body containing name, email and password (password will be hashed).
	 * Returns 201 Created with the created user's DTO (without password) and Location header.
	 */
	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
		UserResponseDTO created = service.create(dto);
		return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
	}

	/**
	 * Retrieve all users. Returns a list of user DTOs.
	 */
	@GetMapping
	public List<UserResponseDTO> getAll() {
		return service.getAll();
	}
}
