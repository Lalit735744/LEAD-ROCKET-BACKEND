// Core business logic for lead management

package com.leadrocket.backend.leads.service;

import com.leadrocket.backend.common.exception.NotFoundException;
import com.leadrocket.backend.leads.dto.LeadRequestDTO;
import com.leadrocket.backend.notifications.service.NotificationService;
import com.leadrocket.backend.leads.dto.LeadResponseDTO;
import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.leads.repository.TenantLeadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadService {

	private final TenantLeadRepository repository;
	private final LeadMapper mapper;
	private final NotificationService notificationService;

	public LeadService(
			TenantLeadRepository repository,
			LeadMapper mapper,
			NotificationService notificationService
	) {
		this.repository = repository;
		this.mapper = mapper;
		this.notificationService = notificationService;
	}

	public LeadResponseDTO create(String companyId, String createdBy, LeadRequestDTO dto) {

		Lead lead = new Lead();
		lead.setCompanyId(companyId);
		lead.setName(dto.getName());
		lead.setPhone(dto.getPhone());
		lead.setEmail(dto.getEmail());
		lead.setSource(dto.getSource());
		lead.setStatus(dto.getStatus());
		lead.setAssignedTo(dto.getAssignedTo());
		lead.setMetadata(dto.getMetadata());

		Lead saved = repository.save(companyId, lead);

		// Notify assigned user if present
		if (saved.getAssignedTo() != null) {
			notificationService.send(
					companyId,
					createdBy,
					saved.getAssignedTo(),
					"LEAD_ASSIGNED",
					"New Lead Assigned",
					"You have been assigned a new lead: " + saved.getName()
			);
		}

		return mapper.toResponse(saved);
	}

	public LeadResponseDTO getById(String companyId, String leadId) {
		Lead lead = repository.findById(companyId, leadId)
				.orElseThrow(() -> new NotFoundException("Lead not found"));
		return mapper.toResponse(lead);
	}

	public List<LeadResponseDTO> getAll(String companyId) {
		return repository.findAll(companyId)
				.stream()
				.map(mapper::toResponse)
				.collect(Collectors.toList());
	}

	public List<LeadResponseDTO> getByUser(String companyId, String userId) {
		return repository.findByAssignedTo(companyId, userId)
				.stream()
				.map(mapper::toResponse)
				.collect(Collectors.toList());
	}

	public LeadResponseDTO update(String companyId, String leadId, LeadRequestDTO dto) {

		Lead lead = repository.findById(companyId, leadId)
				.orElseThrow(() -> new NotFoundException("Lead not found"));

		if (dto.getName() != null) lead.setName(dto.getName());
		if (dto.getPhone() != null) lead.setPhone(dto.getPhone());
		if (dto.getEmail() != null) lead.setEmail(dto.getEmail());
		if (dto.getSource() != null) lead.setSource(dto.getSource());
		if (dto.getStatus() != null) lead.setStatus(dto.getStatus());
		if (dto.getAssignedTo() != null) lead.setAssignedTo(dto.getAssignedTo());
		if (dto.getMetadata() != null) lead.setMetadata(dto.getMetadata());

		return mapper.toResponse(repository.save(companyId, lead));
	}
}
