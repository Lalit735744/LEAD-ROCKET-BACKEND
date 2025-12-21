package com.leadrocket.backend.users.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

/**
 * MongoDB document representing an application user.
 *
 * Notes:
 * - Stored in the 'users' collection.
 * - Passwords are stored as hashed values (never expose raw passwords in responses).
 * - For tenant-aware design, users may be stored in tenant-specific collections.
 * - `companyId` indicates which tenant this user belongs to (if stored in tenant collection this may be redundant).
 * - `roleIds` holds references to role documents in the tenant roles collection.
 */
@Document(collection = "users")
public class User {

	@Id
	private String id; // Mongo document id

	// Basic profile fields
	private String name;
	private String email;
	private String mobile;
	private boolean active;

	// Tenant membership and roles
	private String companyId;
	private List<String> roleIds;

	// Security fields (password should be hashed)
	private String password;
	private Instant passwordChangedAt;

	// Audit / soft-delete
	private boolean deleted = false;
	private Instant createdAt;
	private Instant updatedAt;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }

	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }

	public String getCompanyId() { return companyId; }
	public void setCompanyId(String companyId) { this.companyId = companyId; }

	public List<String> getRoleIds() { return roleIds; }
	public void setRoleIds(List<String> roleIds) { this.roleIds = roleIds; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Instant getPasswordChangedAt() { return passwordChangedAt; }
	public void setPasswordChangedAt(Instant passwordChangedAt) { this.passwordChangedAt = passwordChangedAt; }

	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }

	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
