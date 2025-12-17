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
import com.leadrocket.backend.activities.service.ActivityMapper;

@Service
public class ActivityService {

    private final ActivityRepository repository;
    private final ActivityFilterService filterService;
    private final ActivityMapper mapper;

    public ActivityService(ActivityRepository repository, ActivityFilterService filterService, ActivityMapper mapper) {
        this.repository = repository;
        this.filterService = filterService;
        this.mapper = mapper;
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

        return mapper.toDTO(repository.save(activity));
    }

    @Cacheable(value = "activities", key = "#id")
    public ActivityDTO getById(String id) {
        Activity activity = repository.findById(id).orElseThrow(() -> new NotFoundException("Activity not found: " + id));
        return mapper.toDTO(activity);
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
        return mapper.toDTO(repository.save(activity));
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
        return mapper.toDTO(repository.save(activity));
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
        return mapper.toDTO(repository.save(activity));
    }

    public List<ActivityDTO> getByLead(String leadId) {
        return repository.findByLeadId(leadId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getByUser(String userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getByMetadata(String key, Object value) {
        return filterService.filterByMetadata(key, value)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getNotDone(String userId) {
        return filterService.notDoneActivities(userId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ActivityDTO> getOverdue(String userId) {
        return filterService.overdueActivities(userId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
