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

	// metadata filter
	@GetMapping("/metadata")
	public List<ActivityDTO> byMetadata(@RequestParam String key, @RequestParam String value) {
		return service.getByMetadata(key, value);
	}

	// New endpoints
	@GetMapping("/{id}")
	public ActivityDTO getById(@PathVariable String id) {
		return service.getById(id);
	}

	@PutMapping("/{id}")
	public ActivityDTO update(@PathVariable String id, @RequestBody ActivityDTO dto) {
		dto.setId(id);
		return service.update(dto);
	}

	@PatchMapping("/{id}")
	public ActivityDTO patch(@PathVariable String id, @RequestBody ActivityDTO dto) {
		dto.setId(id);
		return service.patch(dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		service.delete(id);
	}

	@PostMapping("/{id}/remarks")
	public ActivityDTO addRemark(@PathVariable String id, @RequestBody String remark) {
		return service.appendRemark(id, remark);
	}

}
