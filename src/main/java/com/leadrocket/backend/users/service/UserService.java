// Core business logic for tenant users

package com.leadrocket.backend.users.service;

import com.leadrocket.backend.common.exception.ConflictException;
import com.leadrocket.backend.common.exception.NotFoundException;
import com.leadrocket.backend.users.dto.UserCreateRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final TenantUserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public UserService(TenantUserRepository repository,
					   PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponseDTO create(String companyId, UserCreateRequestDTO dto) {

		// Prevent duplicate email per company
		if (repository.findByEmail(companyId, dto.getEmail()).isPresent()) {
			throw new ConflictException("User email already exists");
		}

		User user = new User();
		user.setCompanyId(companyId);
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setMobile(dto.getMobile());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoleIds(dto.getRoleIds());
		user.setActive(true);

		return toDTO(repository.save(companyId, user));
	}

	public List<UserResponseDTO> getAll(String companyId) {
		return repository.findAll(companyId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public UserResponseDTO getById(String companyId, String userId) {
		User user = repository.findById(companyId, userId)
				.orElseThrow(() -> new NotFoundException("User not found"));
		return toDTO(user);
	}

	private UserResponseDTO toDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setMobile(user.getMobile());
		dto.setActive(user.isActive());
		dto.setRoleIds(user.getRoleIds());
		return dto;
	}
}
