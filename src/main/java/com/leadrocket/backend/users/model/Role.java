// Role entity
// Defines permissions for tenant users

package com.leadrocket.backend.users.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Role {

    @Id
    private String id;

    private String name;                // CEO, MANAGER, SALES
    private List<String> permissions;   // LEAD_VIEW, PAYMENT_VIEW, ALL

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}
