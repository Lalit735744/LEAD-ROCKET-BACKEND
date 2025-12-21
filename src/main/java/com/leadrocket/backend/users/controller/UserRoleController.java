package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import com.leadrocket.backend.security.RbacService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for assigning/removing roles to tenant users. Only CEO can perform these actions.
 */
@RestController
@RequestMapping("/api/{companyId}/users")
public class UserRoleController {

    private final RbacService rbacService;
    private final TenantUserRepository tenantUserRepository;

    public UserRoleController(RbacService rbacService, TenantUserRepository tenantUserRepository) {
        this.rbacService = rbacService;
        this.tenantUserRepository = tenantUserRepository;
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<User> assignRoles(@PathVariable String companyId, @PathVariable String userId, HttpServletRequest request, @RequestBody List<String> roleIds) {
        String requesterId = (String) request.getAttribute("authenticatedUserId");
        String tokenCompanyId = (String) request.getAttribute("authenticatedCompanyId");
        if (requesterId == null || tokenCompanyId == null || !tokenCompanyId.equals(companyId)) {
            return ResponseEntity.status(403).build();
        }
        // only CEO can assign roles
        if (!rbacService.userHasRole(companyId, requesterId, "CEO")) {
            return ResponseEntity.status(403).build();
        }
        User u = tenantUserRepository.findById(companyId, userId).orElse(null);
        if (u == null) return ResponseEntity.notFound().build();
        List<String> current = u.getRoleIds();
        if (current == null) current = new ArrayList<>();
        for (String r : roleIds) if (!current.contains(r)) current.add(r);
        u.setRoleIds(current);
        User saved = tenantUserRepository.save(companyId, u);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<User> removeRole(@PathVariable String companyId, @PathVariable String userId, @PathVariable String roleId, HttpServletRequest request) {
        String requesterId = (String) request.getAttribute("authenticatedUserId");
        String tokenCompanyId = (String) request.getAttribute("authenticatedCompanyId");
        if (requesterId == null || tokenCompanyId == null || !tokenCompanyId.equals(companyId)) {
            return ResponseEntity.status(403).build();
        }
        if (!rbacService.userHasRole(companyId, requesterId, "CEO")) {
            return ResponseEntity.status(403).build();
        }
        User u = tenantUserRepository.findById(companyId, userId).orElse(null);
        if (u == null) return ResponseEntity.notFound().build();
        List<String> current = u.getRoleIds();
        if (current != null) current.removeIf(r -> r.equals(roleId));
        u.setRoleIds(current);
        User saved = tenantUserRepository.save(companyId, u);
        return ResponseEntity.ok(saved);
    }
}

