package com.leadrocket.backend.security;

import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantRoleRepository;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Lightweight RBAC service that resolves roles/permissions for tenant users.
 */
@Service
public class RbacService {

    private final TenantRoleRepository roleRepository;
    private final TenantUserRepository tenantUserRepository;

    public RbacService(TenantRoleRepository roleRepository, TenantUserRepository tenantUserRepository) {
        this.roleRepository = roleRepository;
        this.tenantUserRepository = tenantUserRepository;
    }

    /**
     * Check whether the given user (in company) has a role with the given name.
     */
    public boolean userHasRole(String companyId, String userId, String roleName) {
        User user = tenantUserRepository.findById(companyId, userId).orElse(null);
        if (user == null) return false;
        List<String> roleIds = user.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) return false;
        for (String rid : roleIds) {
            Optional<Role> r = roleRepository.findById(companyId, rid);
            if (r.isPresent() && roleName.equalsIgnoreCase(r.get().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the user has a permission key (resolves roles -> permissions).
     * Permission matches if any role contains the permission or has 'ALL'.
     */
    public boolean hasPermission(String companyId, String userId, String permissionKey) {
        User user = tenantUserRepository.findById(companyId, userId).orElse(null);
        if (user == null) return false;
        List<String> roleIds = user.getRoleIds();
        if (roleIds == null || roleIds.isEmpty()) return false;
        for (String rid : roleIds) {
            Optional<Role> r = roleRepository.findById(companyId, rid);
            if (r.isPresent()) {
                List<String> perms = r.get().getPermissions();
                if (perms == null) continue;
                if (perms.contains("ALL")) return true;
                if (perms.contains(permissionKey)) return true;
            }
        }
        return false;
    }

    public Role createRole(String companyId, Role role) {
        return roleRepository.save(companyId, role);
    }

    public List<Role> getRoles(String companyId) {
        return roleRepository.findAll(companyId);
    }
}
