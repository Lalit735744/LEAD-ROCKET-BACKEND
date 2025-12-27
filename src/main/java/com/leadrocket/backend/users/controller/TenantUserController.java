// Tenant-scoped REST APIs for user management

package com.leadrocket.backend.users.controller;

import com.leadrocket.backend.users.dto.UserCreateRequestDTO;
import com.leadrocket.backend.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/users")
public class TenantUserController {

    private final UserService service;

    public TenantUserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponseDTO create(
            @PathVariable String companyId,
            @Valid @RequestBody UserCreateRequestDTO dto
    ) {
        return service.create(companyId, dto);
    }

    @GetMapping
    public List<UserResponseDTO> list(@PathVariable String companyId) {
        return service.getAll(companyId);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO get(
            @PathVariable String companyId,
            @PathVariable String userId
    ) {
        return service.getById(companyId, userId);
    }
}
