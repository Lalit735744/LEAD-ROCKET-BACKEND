// Core business logic for activities

package com.leadrocket.backend.activities.service;

import com.leadrocket.backend.activities.dto.ActivityRequestDTO;
import com.leadrocket.backend.activities.dto.ActivityResponseDTO;
import com.leadrocket.backend.activities.model.Activity;
import com.leadrocket.backend.activities.repository.TenantActivityRepository;
import com.leadrocket.backend.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final TenantActivityRepository repository;
    private final ActivityMapper mapper;

    public ActivityService(TenantActivityRepository repository, ActivityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ActivityResponseDTO create(String companyId, ActivityRequestDTO dto) {
        Activity a = new Activity();
        a.setCompanyId(companyId);
        a.setLeadId(dto.getLeadId());
        a.setUserId(dto.getUserId());
        a.setType(dto.getType());
        a.setStatus(dto.getStatus());
        a.setRemarks(dto.getRemarks());
        a.setFollowUpDate(dto.getFollowUpDate());
        a.setMetadata(dto.getMetadata());
        return mapper.toResponse(repository.save(companyId, a));
    }

    public ActivityResponseDTO getById(String companyId, String id) {
        Activity a = repository.findById(companyId, id)
                .orElseThrow(() -> new NotFoundException("Activity not found"));
        return mapper.toResponse(a);
    }

    public List<ActivityResponseDTO> byLead(String companyId, String leadId) {
        return repository.findByLead(companyId, leadId)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ActivityResponseDTO> byUser(String companyId, String userId) {
        return repository.findByUser(companyId, userId)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ActivityResponseDTO> pending(String companyId, String userId) {
        return repository.findPendingByUser(companyId, userId)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public List<ActivityResponseDTO> overdue(String companyId, String userId) {
        return repository.findOverdueByUser(companyId, userId)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
