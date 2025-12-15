package com.leadrocket.backend.leads.assignment;

import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LeadAssignmentService {

	private final LeadRepository repository;

	public LeadAssignmentService(LeadRepository repository) {
		this.repository = repository;
	}

	public Lead assign(String leadId, String userId) {
		Lead lead = repository.findById(leadId).orElseThrow();
		lead.setAssignedTo(userId);
		lead.setUpdatedAt(new Date());
		return repository.save(lead);
	}

	public Lead unassign(String leadId) {
		Lead lead = repository.findById(leadId).orElseThrow();
		lead.setAssignedTo(null);
		lead.setUpdatedAt(new Date());
		return repository.save(lead);
	}
}
