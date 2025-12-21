package com.leadrocket.backend.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * DTO used to accept user registration requests.
 *
 * Validation annotations ensure required fields are present and have expected formats.
 */
public class UserRequestDTO {

	@NotBlank(message = "name is required")
	private String name;

	@Email(message = "invalid email")
	@NotBlank(message = "email is required")
	private String email;

	private String mobile;

	@NotBlank(message = "password is required")
	private String password;

	// Optional: when provided, create user inside tenant (company) collections
	private String companyId;

	// Optional role assignments for tenant users
	private List<String> roleIds;

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getCompanyId() { return companyId; }
	public void setCompanyId(String companyId) { this.companyId = companyId; }

	public List<String> getRoleIds() { return roleIds; }
	public void setRoleIds(List<String> roleIds) { this.roleIds = roleIds; }
}
