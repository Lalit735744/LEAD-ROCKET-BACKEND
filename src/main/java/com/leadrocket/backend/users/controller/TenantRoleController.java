// Tenant-scoped APIs for role management
// Only CEO should be allowed (RBAC enforced later at security layer)

package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/roles")
public class TenantRoleController {

    private final RoleService service;

    public TenantRoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping
    public Role create(
            @PathVariable String companyId,
            @RequestBody Role role
    ) {
        return service.create(companyId, role);
    }

    @GetMapping
    public List<Role> list(@PathVariable String companyId) {
        return service.list(companyId);
    }
}
