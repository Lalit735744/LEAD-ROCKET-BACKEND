package com.leadrocket.backend.leads.repository;

import com.leadrocket.backend.leads.model.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LeadRepository extends MongoRepository<Lead, String> {

	List<Lead> findByAssignedTo(String userId);

	List<Lead> findByStatus(String status);
}
