package com.leadrocket.backend.users.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "users")
public class User {

	@Id
	private String id;

	private String name;
	private String email;
	private String mobile;
	private boolean active;

	// security fields
	private String password;
	private Instant passwordChangedAt;

	// audit / soft-delete
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
