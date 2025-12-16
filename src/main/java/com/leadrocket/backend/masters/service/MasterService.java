package com.leadrocket.backend.masters.service;

import com.leadrocket.backend.masters.model.MasterData;
import com.leadrocket.backend.masters.repository.MasterRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class MasterService {

	private final MasterRepository repository;

	public MasterService(MasterRepository repository) {
		this.repository = repository;
	}

	@Cacheable(value = "masters", key = "#type")
	public List<MasterData> getByType(String type) {
		return repository.findByType(type);
	}
}
