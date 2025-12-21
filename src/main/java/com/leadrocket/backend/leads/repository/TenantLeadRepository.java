package com.leadrocket.backend.leads.repository;

import com.leadrocket.backend.leads.model.Lead;

import java.util.List;
import java.util.Optional;

public interface TenantLeadRepository {
    Lead save(String companyId, Lead lead);
    Optional<Lead> findById(String companyId, String id);
    List<Lead> findByAssignedTo(String companyId, String userId);
}

