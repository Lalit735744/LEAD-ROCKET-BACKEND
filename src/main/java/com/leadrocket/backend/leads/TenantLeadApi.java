package com.leadrocket.backend.leads;

import com.leadrocket.backend.leads.model.Lead;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Tenant-aware REST API for lead operations under /api/{companyId}/leads.
 */
@RestController
@RequestMapping("/api/{companyId}/leads")
public class TenantLeadApi {

    private final LeadNewService leadNewService;

    public TenantLeadApi(LeadNewService leadNewService) {
        this.leadNewService = leadNewService;
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(
            @PathVariable String companyId,
            HttpServletRequest request,
            @RequestBody Lead lead
    ) {
        // authenticatedUserId set by JwtFilter
        String createdBy = (String) request.getAttribute("authenticatedUserId");
        Lead saved = leadNewService.createLead(companyId, lead, createdBy);
        return ResponseEntity.ok(saved);
    }
}
