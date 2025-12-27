// User entity
// Represents either a platform user or a tenant user

package com.leadrocket.backend.users.model;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.annotation.Id;

import java.util.List;

public class User extends BaseEntity {

	@Id
	private String id;

	// PLATFORM or TENANT
	private UserType userType = UserType.TENANT;

	// Only set for TENANT users
	private String companyId;

	private String name;
	private String email;
	private String mobile;

	private String password;
	private boolean active = true;

	// RBAC (used only for TENANT users)
	private List<String> roleIds;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public UserType getUserType() { return userType; }
	public void setUserType(UserType userType) { this.userType = userType; }

	public String getCompanyId() { return companyId; }
	public void setCompanyId(String companyId) { this.companyId = companyId; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getMobile() { return mobile; }
	public void setMobile(String mobile) { this.mobile = mobile; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }

	public List<String> getRoleIds() { return roleIds; }
	public void setRoleIds(List<String> roleIds) { this.roleIds = roleIds; }
}
