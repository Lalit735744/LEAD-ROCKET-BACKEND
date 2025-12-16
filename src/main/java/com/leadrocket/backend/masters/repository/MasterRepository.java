package com.leadrocket.backend.masters.repository;

import com.leadrocket.backend.masters.model.MasterData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MasterRepository extends MongoRepository<MasterData, String> {
	List<MasterData> findByType(String type);
}

