package com.leadrocket.backend.users.dto;

import java.util.List;

// Response DTO for user APIs
public class UserResponseDTO {

    private String id;
    private String name;
    private String email;
    private String mobile;
    private boolean active;

    // Tenant-specific
    private List<String> roleIds;

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

    public List<String> getRoleIds() { return roleIds; }
    public void setRoleIds(List<String> roleIds) { this.roleIds = roleIds; }
}
