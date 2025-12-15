package com.leadrocket.backend.activities.controller;

import com.leadrocket.backend.activities.dto.ActivityDTO;
import com.leadrocket.backend.activities.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
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
	@GetMapping("/not_done/{userId}")
	public List<ActivityDTO> notDone(@PathVariable String userId) {
		return service.getNotDone(userId);
	}

	@GetMapping("/overdue/{userId}")
	public List<ActivityDTO> overdue(@PathVariable String userId) {
		return service.getOverdue(userId);
	}

}
