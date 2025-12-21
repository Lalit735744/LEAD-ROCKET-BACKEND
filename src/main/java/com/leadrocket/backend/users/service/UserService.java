package com.leadrocket.backend.users.service;

import com.leadrocket.backend.users.dto.UserRequestDTO;
import com.leadrocket.backend.users.dto.UserResponseDTO;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import com.leadrocket.backend.users.repository.UserRepository;
import com.leadrocket.backend.common.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for user-related business logic.
 *
 * Supports both global users (legacy behavior) and tenant-scoped users. If the
 * incoming DTO contains a companyId, the user will be persisted into the
 * tenant-specific collection using TenantUserRepository.
 */
@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository repository; // global users collection
	private final TenantUserRepository tenantRepository; // tenant-aware
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository repository, TenantUserRepository tenantRepository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.tenantRepository = tenantRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Create a new user. If dto.companyId is set, user will be saved into the
	 * tenant collection for that company; otherwise the global users collection
	 * will be used.
	 */
	public UserResponseDTO create(UserRequestDTO dto) {
		if (dto.getCompanyId() == null) {
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
			log.info("Created global user id={} email={}", saved.getId(), saved.getEmail());
			return toDTO(saved);
		} else {
			String companyId = dto.getCompanyId();
			if (dto.getEmail() != null && tenantRepository.existsByEmail(companyId, dto.getEmail())) {
				throw new ConflictException("Email already in use for company");
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
			// set tenant membership
			user.setCompanyId(companyId);
			// set role assignments if provided
			if (dto.getRoleIds() != null) {
				user.setRoleIds(dto.getRoleIds());
			}
			User saved = tenantRepository.save(companyId, user);
			log.info("Created tenant user company={} id={} email={}", companyId, saved.getId(), saved.getEmail());
			return toDTO(saved);
		}
	}

	/**
	 * Change user password in global or tenant store depending on where user resides.
	 * For tenant users, caller should supply companyId and userId to modify.
	 */
	public void changePassword(String userId, String oldPassword, String newPassword) {
		// This method currently handles global users only. Tenant password changes
		// will be implemented in tenant-aware service methods later.
		User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		if (user.getPassword() != null && !passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new RuntimeException("Invalid current password");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setPasswordChangedAt(Instant.now());
		repository.save(user);
	}

	/**
	 * Get all users (global). Tenant-scoped listing should call tenantRepository directly.
	 */
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
