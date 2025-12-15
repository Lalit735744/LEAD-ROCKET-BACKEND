package com.leadrocket.backend.leads.controller;

import com.leadrocket.backend.leads.assignment.LeadAssignmentService;
import com.leadrocket.backend.leads.dto.LeadServiceDTO;
import com.leadrocket.backend.leads.service.LeadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leads")
public class LeadController {

	private final LeadService service;
	private final LeadAssignmentService assignmentService;

	public LeadController(LeadService service, LeadAssignmentService assignmentService) {
		this.service = service;
		this.assignmentService = assignmentService;
	}

	@PostMapping("/create")
	public LeadServiceDTO create(@RequestBody LeadServiceDTO dto) {
		return service.create(dto);
	}

	@GetMapping("/users")
	public List<LeadServiceDTO> getAll() {
		return service.getAll();
	}

	@GetMapping("/user/{userId}")
	public List<LeadServiceDTO> getByUser(@PathVariable String userId) {
		return service.getByUser(userId);
	}

	@PatchMapping("/{leadId}/status/{status}")
	public LeadServiceDTO updateStatus(@PathVariable String leadId,
	                                   @PathVariable String status) {
		return service.updateStatus(leadId, status);
	}

	@PutMapping("/{leadId}/assign/{userId}")
	public LeadServiceDTO assign(@PathVariable String leadId, @PathVariable String userId){
		return service.toDTO(assignmentService.assign(leadId, userId));
	}

	@DeleteMapping("/{leadId}")
	public void softDelete(@PathVariable String leadId){
		service.softDelete(leadId);
	}
}
