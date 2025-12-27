// Central RBAC resolver
// NEVER check roles directly in controllers

package com.leadrocket.backend.security.rbac;

import com.leadrocket.backend.common.exception.ForbiddenException;
import com.leadrocket.backend.security.context.AuthContext;
import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantRoleRepository;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RbacService {

    private final TenantUserRepository userRepository;
    private final TenantRoleRepository roleRepository;

    public RbacService(
            TenantUserRepository userRepository,
            TenantRoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void requireRole(String roleName) {

        User user = currentUser();
        if (user.getRoleIds() == null) {
            throw new ForbiddenException("Access denied");
        }

        for (String roleId : user.getRoleIds()) {
            Role role = roleRepository
                    .findById(user.getCompanyId(), roleId)
                    .orElse(null);

            if (role != null && roleName.equals(role.getName())) {
                return;
            }
        }

        throw new ForbiddenException("Role required: " + roleName);
    }

    public void requirePermission(String permission) {

        User user = currentUser();
        if (user.getRoleIds() == null) {
            throw new ForbiddenException("Access denied");
        }

        for (String roleId : user.getRoleIds()) {
            Role role = roleRepository
                    .findById(user.getCompanyId(), roleId)
                    .orElse(null);

            if (role == null || role.getPermissions() == null) continue;

            if (role.getPermissions().contains("ALL") ||
                    role.getPermissions().contains(permission)) {
                return;
            }
        }

        throw new ForbiddenException("Permission required: " + permission);
    }

    private User currentUser() {
        var session = AuthContext.get();
        if (session == null) {
            throw new ForbiddenException("Unauthenticated");
        }
        return userRepository
                .findById(session.getCompanyId(), session.getUserId())
                .orElseThrow(() -> new ForbiddenException("User not found"));
    }
}
