// Maps Lead entity to response DTO

package com.leadrocket.backend.leads.service;

import com.leadrocket.backend.leads.dto.LeadResponseDTO;
import com.leadrocket.backend.leads.model.Lead;
import org.springframework.stereotype.Component;

@Component
public class LeadMapper {

    public LeadResponseDTO toResponse(Lead lead) {
        LeadResponseDTO dto = new LeadResponseDTO();
        dto.setId(lead.getId());
        dto.setName(lead.getName());
        dto.setPhone(lead.getPhone());
        dto.setEmail(lead.getEmail());
        dto.setSource(lead.getSource());
        dto.setStatus(lead.getStatus());
        dto.setAssignedTo(lead.getAssignedTo());
        dto.setCreatedAt(lead.getCreatedAt());
        dto.setMetadata(lead.getMetadata());
        return dto;
    }
}
