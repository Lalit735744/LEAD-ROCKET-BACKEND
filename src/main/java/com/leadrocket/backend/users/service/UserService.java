package com.leadrocket.backend.users.service;

import com.leadrocket.backend.users.dto.UserRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public UserResponseDTO create(UserRequestDTO dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setMobile(dto.getMobile());
		user.setActive(true);
		return toDTO(repository.save(user));
	}

	public List<UserResponseDTO> getAll() {
		return repository.findAll()
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	private UserResponseDTO toDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setMobile(user.getMobile());
		dto.setActive(user.isActive());
		return dto;
	}
}
