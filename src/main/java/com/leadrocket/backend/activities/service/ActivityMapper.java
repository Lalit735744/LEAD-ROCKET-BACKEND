// Maps Activity entity to response DTO

package com.leadrocket.backend.activities.service;

import com.leadrocket.backend.activities.dto.ActivityResponseDTO;
import com.leadrocket.backend.activities.model.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityResponseDTO toResponse(Activity a) {
        ActivityResponseDTO dto = new ActivityResponseDTO();
        dto.setId(a.getId());
        dto.setLeadId(a.getLeadId());
        dto.setUserId(a.getUserId());
        dto.setType(a.getType());
        dto.setStatus(a.getStatus());
        dto.setRemarks(a.getRemarks());
        dto.setFollowUpDate(a.getFollowUpDate());
        dto.setCreatedAt(a.getCreatedAt() != null ? a.getCreatedAt().toEpochMilli() != 0 ?
                java.util.Date.from(a.getCreatedAt()) : null : null);
        dto.setMetadata(a.getMetadata());
        return dto;
    }
}
