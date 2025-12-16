package com.leadrocket.backend.activities.service;

import com.leadrocket.backend.activities.dto.ActivityDTO;
import com.leadrocket.backend.activities.model.Activity;
import com.leadrocket.backend.activities.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.leadrocket.backend.activities.filter.ActivityFilterService;
import com.leadrocket.backend.common.exception.NotFoundException;

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

	@Cacheable(value = "activities", key = "#id")
	public ActivityDTO getById(String id) {
		Activity activity = repository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found: " + id));
		return toDTO(activity);
	}

	@CacheEvict(value = "activities", key = "#dto.id")
	public ActivityDTO update(ActivityDTO dto) {
		Activity activity = repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Activity not found: " + dto.getId()));
		activity.setLeadId(dto.getLeadId());
		activity.setUserId(dto.getUserId());
		activity.setActivityType(dto.getActivityType());
		activity.setStatus(dto.getStatus());
		activity.setRemarks(dto.getRemarks());
		activity.setFollowUpDate(dto.getFollowUpDate());
		activity.setMetadata(dto.getMetadata());
		return toDTO(repository.save(activity));
	}

	@CacheEvict(value = "activities", key = "#id")
	public ActivityDTO patch(ActivityDTO dto) {
		Activity activity = repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Activity not found: " + dto.getId()));
		if (dto.getLeadId() != null) activity.setLeadId(dto.getLeadId());
		if (dto.getUserId() != null) activity.setUserId(dto.getUserId());
		if (dto.getActivityType() != null) activity.setActivityType(dto.getActivityType());
		if (dto.getStatus() != null) activity.setStatus(dto.getStatus());
		if (dto.getRemarks() != null) activity.setRemarks(dto.getRemarks());
		if (dto.getFollowUpDate() != null) activity.setFollowUpDate(dto.getFollowUpDate());
		if (dto.getMetadata() != null) activity.setMetadata(dto.getMetadata());
		return toDTO(repository.save(activity));
	}

	@CacheEvict(value = "activities", key = "#id")
	public void delete(String id) {
		repository.deleteById(id);
	}

	@CacheEvict(value = "activities", key = "#id")
	public ActivityDTO appendRemark(String id, String remark) {
		Activity activity = repository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found: " + id));
		String current = activity.getRemarks();
		String appended = (current == null ? "" : current + "\n") + new Date() + ": " + remark;
		activity.setRemarks(appended);
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

	public List<ActivityDTO> getByMetadata(String key, Object value) {
		return filterService.filterByMetadata(key, value)
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
