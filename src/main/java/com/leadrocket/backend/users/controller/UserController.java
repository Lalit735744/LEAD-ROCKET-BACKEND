package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.dto.UserRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping
	public UserResponseDTO create(@RequestBody UserRequestDTO dto) {
		return service.create(dto);
	}

	@GetMapping
	public List<UserResponseDTO> getAll() {
		return service.getAll();
	}
}
