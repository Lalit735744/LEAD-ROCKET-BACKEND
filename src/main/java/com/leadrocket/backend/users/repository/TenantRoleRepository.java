package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.Role;

import java.util.List;
import java.util.Optional;

public interface TenantRoleRepository {
    Role save(String companyId, Role role);
    Optional<Role> findById(String companyId, String id);
    List<Role> findAll(String companyId);
}

