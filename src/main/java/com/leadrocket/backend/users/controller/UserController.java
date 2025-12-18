package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.dto.UserRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
		UserResponseDTO created = service.create(dto);
		return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
	}

	@GetMapping
	public List<UserResponseDTO> getAll() {
		return service.getAll();
	}
}
