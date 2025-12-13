package com.leadrocket.backend.activities.controller;

import com.leadrocket.backend.activities.dto.ActivityDTO;
import com.leadrocket.backend.activities.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {

	private final ActivityService service;

	public ActivityController(ActivityService service) {
		this.service = service;
	}

	@PostMapping
	public ActivityDTO create(@RequestBody ActivityDTO dto) {
		return service.create(dto);
	}

	@GetMapping("/lead/{leadId}")
	public List<ActivityDTO> getByLead(@PathVariable String leadId) {
		return service.getByLead(leadId);
	}

	@GetMapping("/user/{userId}")
	public List<ActivityDTO> getByUser(@PathVariable String userId) {
		return service.getByUser(userId);
	}
}
