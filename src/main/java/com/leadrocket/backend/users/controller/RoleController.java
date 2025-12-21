package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.security.RbacService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to manage tenant roles. Only CEO can create roles (enforced at runtime using RBAC checks).
 */
@RestController
@RequestMapping("/api/{companyId}/roles")
public class RoleController {

    private final RbacService rbacService;

    public RoleController(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@PathVariable String companyId, HttpServletRequest request, @RequestBody Role role) {
        // requester id and company id are set by JwtFilter into request attributes
        String requesterId = (String) request.getAttribute("authenticatedUserId");
        String tokenCompanyId = (String) request.getAttribute("authenticatedCompanyId");
        // ensure token company matches path company (basic guard)
        if (tokenCompanyId == null || !tokenCompanyId.equals(companyId)) {
            return ResponseEntity.status(403).build();
        }
        // verify requester is CEO for the company
        if (requesterId == null || !rbacService.userHasRole(companyId, requesterId, "CEO")) {
            return ResponseEntity.status(403).build();
        }
        Role saved = rbacService.createRole(companyId, role);
        return ResponseEntity.ok(saved);
    }

    // listing roles is allowed for authenticated users
    @GetMapping
    public ResponseEntity<List<Role>> listRoles(@PathVariable String companyId) {
        List<Role> roles = rbacService.getRoles(companyId);
        return ResponseEntity.ok(roles);
    }
}
