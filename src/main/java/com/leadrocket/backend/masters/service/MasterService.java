// Service layer for master data
// Keeps controller clean and allows future caching

package com.leadrocket.backend.masters.service;

import com.leadrocket.backend.masters.model.MasterData;
import com.leadrocket.backend.masters.repository.MasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {

	private final MasterRepository repository;

	public MasterService(MasterRepository repository) {
		this.repository = repository;
	}

	/**
	 * Get active master values by type.
	 * Example: LEAD_STATUS, ACTIVITY_TYPE
	 */
	public List<MasterData> getByType(String type) {
		return repository.findByTypeAndActiveTrue(type);
	}
}
