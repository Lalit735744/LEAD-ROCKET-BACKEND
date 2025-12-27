// Role management service (tenant-scoped)

package com.leadrocket.backend.users.service;

import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.repository.TenantRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final TenantRoleRepository repository;

    public RoleService(TenantRoleRepository repository) {
        this.repository = repository;
    }

    public Role create(String companyId, Role role) {
        return repository.save(companyId, role);
    }

    public List<Role> list(String companyId) {
        return repository.findAll(companyId);
    }
}
