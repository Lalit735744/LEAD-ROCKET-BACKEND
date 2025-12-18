package com.leadrocket.backend.users.service;

import com.leadrocket.backend.users.dto.UserRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.UserRepository;
import com.leadrocket.backend.common.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponseDTO create(UserRequestDTO dto) {
		if (dto.getEmail() != null && repository.existsByEmail(dto.getEmail())) {
			throw new ConflictException("Email already in use");
		}

		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setMobile(dto.getMobile());
		user.setActive(true);
		if (dto.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			user.setPasswordChangedAt(Instant.now());
		}
		user.setCreatedAt(Instant.now());
		user.setUpdatedAt(Instant.now());
		User saved = repository.save(user);
		log.info("Created user id={} email={}", saved.getId(), saved.getEmail());
		return toDTO(saved);
	}

	public void changePassword(String userId, String oldPassword, String newPassword) {
		User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		if (user.getPassword() != null && !passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new RuntimeException("Invalid current password");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setPasswordChangedAt(Instant.now());
		repository.save(user);
	}

	public List<UserResponseDTO> getAll() {
		return repository.findAll()
			.stream()
			.map(this::toDTO)
			.collect(Collectors.toList());
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
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
