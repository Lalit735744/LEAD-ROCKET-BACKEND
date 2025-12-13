package com.leadrocket.backend.leads.controller;

import com.leadrocket.backend.leads.dto.LeadServiceDTO;
import com.leadrocket.backend.leads.service.LeadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leads")
public class LeadController {

	private final LeadService service;

	public LeadController(LeadService service) {
		this.service = service;
	}

	@PostMapping
	public LeadServiceDTO create(@RequestBody LeadServiceDTO dto) {
		return service.create(dto);
	}

	@GetMapping
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
}
