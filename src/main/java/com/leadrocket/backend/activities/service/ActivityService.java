package com.leadrocket.backend.activities.service;

import com.leadrocket.backend.activities.dto.ActivityDTO;
import com.leadrocket.backend.activities.model.Activity;
import com.leadrocket.backend.activities.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.leadrocket.backend.activities.filter.ActivityFilterService;

@Service
public class ActivityService {

	private final ActivityRepository repository;
	private final ActivityFilterService filterService;

	public ActivityService(ActivityRepository repository, ActivityFilterService filterService) {
		this.repository = repository;
		this.filterService = filterService;
	}

	public ActivityDTO create(ActivityDTO dto) {
		Activity activity = new Activity();
		activity.setLeadId(dto.getLeadId());
		activity.setUserId(dto.getUserId());
		activity.setActivityType(dto.getActivityType());
		activity.setStatus(dto.getStatus());
		activity.setRemarks(dto.getRemarks());
		activity.setFollowUpDate(dto.getFollowUpDate());
		activity.setMetadata(dto.getMetadata());
		activity.setCreatedAt(new Date());

		return toDTO(repository.save(activity));
	}

	public List<ActivityDTO> getByLead(String leadId) {
		return repository.findByLeadId(leadId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public List<ActivityDTO> getByUser(String userId) {
		return repository.findByUserId(userId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public List<ActivityDTO> getNotDone(String userId) {
		return filterService.notDoneActivities(userId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public List<ActivityDTO> getOverdue(String userId) {
		return filterService.overdueActivities(userId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public ActivityDTO toDTO(Activity activity) {
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
