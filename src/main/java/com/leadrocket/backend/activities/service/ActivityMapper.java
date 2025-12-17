package com.leadrocket.backend.activities.service;

import com.leadrocket.backend.activities.dto.ActivityDTO;
import com.leadrocket.backend.activities.model.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO toDTO(Activity activity) {
        if (activity == null) return null;
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setLeadId(activity.getLeadId());
        dto.setUserId(activity.getUserId());
        dto.setActivityType(activity.getActivityType());
        dto.setStatus(activity.getStatus());
        dto.setRemarks(activity.getRemarks());
        dto.setFollowUpDate(activity.getFollowUpDate());
        dto.setCreatedAt(activity.getCreatedAt());
        dto.setMetadata(activity.getMetadata());
        return dto;
    }
}

