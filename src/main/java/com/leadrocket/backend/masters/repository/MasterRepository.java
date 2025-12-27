// Repository for master data
// Masters are global and shared across all companies

package com.leadrocket.backend.masters.repository;

import com.leadrocket.backend.masters.model.MasterData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MasterRepository extends MongoRepository<MasterData, String> {

	// Fetch only active masters for a given type
	List<MasterData> findByTypeAndActiveTrue(String type);
}
