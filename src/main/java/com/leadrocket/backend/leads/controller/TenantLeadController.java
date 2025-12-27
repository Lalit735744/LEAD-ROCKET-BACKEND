// Tenant-scoped REST APIs for lead management

package com.leadrocket.backend.leads.controller;

import com.leadrocket.backend.leads.dto.LeadRequestDTO;
import com.leadrocket.backend.leads.dto.LeadResponseDTO;
import com.leadrocket.backend.leads.service.LeadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/leads")
public class TenantLeadController {

    private final LeadService service;

    public TenantLeadController(LeadService service) {
        this.service = service;
    }

    @PostMapping
    public LeadResponseDTO create(
            @PathVariable String companyId,
            @RequestBody LeadRequestDTO dto,
            HttpServletRequest request
    ) {
        String createdBy = (String) request.getAttribute("authenticatedUserId");
        return service.create(companyId, createdBy, dto);
    }

    @GetMapping
    public List<LeadResponseDTO> getAll(@PathVariable String companyId) {
        return service.getAll(companyId);
    }

    @GetMapping("/{leadId}")
    public LeadResponseDTO getById(
            @PathVariable String companyId,
            @PathVariable String leadId
    ) {
        return service.getById(companyId, leadId);
    }

    @GetMapping("/user/{userId}")
    public List<LeadResponseDTO> getByUser(
            @PathVariable String companyId,
            @PathVariable String userId
    ) {
        return service.getByUser(companyId, userId);
    }

    @PutMapping("/{leadId}")
    public LeadResponseDTO update(
            @PathVariable String companyId,
            @PathVariable String leadId,
            @RequestBody LeadRequestDTO dto
    ) {
        return service.update(companyId, leadId, dto);
    }
}
