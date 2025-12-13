package com.leadrocket.backend.leads.service;

import com.leadrocket.backend.leads.dto.LeadServiceDTO;
import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadService {

	private final LeadRepository repository;

	public LeadService(LeadRepository repository) {
		this.repository = repository;
	}

	public LeadServiceDTO create(LeadServiceDTO dto) {
		Lead lead = new Lead();
		lead.setName(dto.getName());
		lead.setPhone(dto.getPhone());
		lead.setEmail(dto.getEmail());
		lead.setSource(dto.getSource());
		lead.setStatus(dto.getStatus());
		lead.setAssignedTo(dto.getAssignedTo());
		lead.setMetadata(dto.getMetadata());
		lead.setCreatedAt(new Date());
		lead.setUpdatedAt(new Date());

		return toDTO(repository.save(lead));
	}

	public List<LeadServiceDTO> getAll() {
		return repository.findAll()
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public List<LeadServiceDTO> getByUser(String userId) {
		return repository.findByAssignedTo(userId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public LeadServiceDTO updateStatus(String leadId, String status) {
		Lead lead = repository.findById(leadId).orElseThrow();
		lead.setStatus(status);
		lead.setUpdatedAt(new Date());
		return toDTO(repository.save(lead));
	}

	private LeadServiceDTO toDTO(Lead lead) {
		LeadServiceDTO dto = new LeadServiceDTO();
		dto.setId(lead.getId());
		dto.setName(lead.getName());
		dto.setPhone(lead.getPhone());
		dto.setEmail(lead.getEmail());
		dto.setSource(lead.getSource());
		dto.setStatus(lead.getStatus());
		dto.setAssignedTo(lead.getAssignedTo());
		dto.setCreatedAt(lead.getCreatedAt());
		dto.setUpdatedAt(lead.getUpdatedAt());
		dto.setMetadata(lead.getMetadata());
		return dto;
	}
}
