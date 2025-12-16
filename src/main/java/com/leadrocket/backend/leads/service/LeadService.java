package com.leadrocket.backend.leads.service;

import com.leadrocket.backend.leads.dto.LeadServiceDTO;
import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.leads.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.leadrocket.backend.common.exception.NotFoundException;

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
	@CacheEvict(value = "leads", key = "#leadId")
	public void softDelete(String leadId) {
		Lead lead = repository.findById(leadId).orElseThrow(() -> new NotFoundException("Lead not found: " + leadId));
		lead.setDeleted(true);
		lead.setUpdatedAt(new Date());
		repository.save(lead);
	}


	public List<LeadServiceDTO> getAll() {
		return repository.findAll()
				.stream()
				.filter(l -> l.getDeleted() == null || !l.getDeleted())
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public List<LeadServiceDTO> getByUser(String userId) {
		return repository.findByAssignedTo(userId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	@CacheEvict(value = "leads", key = "#leadId")
	public LeadServiceDTO updateStatus(String leadId, String status) {
		Lead lead = repository.findById(leadId).orElseThrow(() -> new NotFoundException("Lead not found: " + leadId));
		lead.setStatus(status);
		lead.setUpdatedAt(new Date());
		return toDTO(repository.save(lead));
	}

	@Cacheable(value = "leads", key = "#id")
	public LeadServiceDTO getById(String id) {
		Lead lead = repository.findById(id).orElseThrow(() -> new NotFoundException("Lead not found: " + id));
		return toDTO(lead);
	}

	@CacheEvict(value = "leads", key = "#dto.id")
	public LeadServiceDTO update(LeadServiceDTO dto) {
		Lead lead = repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Lead not found: " + dto.getId()));
		if (dto.getName() != null) lead.setName(dto.getName());
		if (dto.getPhone() != null) lead.setPhone(dto.getPhone());
		if (dto.getEmail() != null) lead.setEmail(dto.getEmail());
		if (dto.getSource() != null) lead.setSource(dto.getSource());
		if (dto.getStatus() != null) lead.setStatus(dto.getStatus());
		if (dto.getAssignedTo() != null) lead.setAssignedTo(dto.getAssignedTo());
		if (dto.getMetadata() != null) lead.setMetadata(dto.getMetadata());
		lead.setUpdatedAt(new Date());
		return toDTO(repository.save(lead));
	}

	public LeadServiceDTO toDTO(Lead lead) {
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
