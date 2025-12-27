// Tenant-scoped REST APIs for activities

package com.leadrocket.backend.activities.controller;

import com.leadrocket.backend.activities.dto.ActivityRequestDTO;
import com.leadrocket.backend.activities.dto.ActivityResponseDTO;
import com.leadrocket.backend.activities.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/activities")
public class TenantActivityController {

    private final ActivityService service;

    public TenantActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping
    public ActivityResponseDTO create(@PathVariable String companyId,
                                      @RequestBody ActivityRequestDTO dto) {
        return service.create(companyId, dto);
    }

    @GetMapping("/{id}")
    public ActivityResponseDTO get(@PathVariable String companyId, @PathVariable String id) {
        return service.getById(companyId, id);
    }

    @GetMapping("/lead/{leadId}")
    public List<ActivityResponseDTO> byLead(@PathVariable String companyId,
                                            @PathVariable String leadId) {
        return service.byLead(companyId, leadId);
    }

    @GetMapping("/user/{userId}")
    public List<ActivityResponseDTO> byUser(@PathVariable String companyId,
                                            @PathVariable String userId) {
        return service.byUser(companyId, userId);
    }

    @GetMapping("/user/{userId}/pending")
    public List<ActivityResponseDTO> pending(@PathVariable String companyId,
                                             @PathVariable String userId) {
        return service.pending(companyId, userId);
    }

    @GetMapping("/user/{userId}/overdue")
    public List<ActivityResponseDTO> overdue(@PathVariable String companyId,
                                             @PathVariable String userId) {
        return service.overdue(companyId, userId);
    }
}
