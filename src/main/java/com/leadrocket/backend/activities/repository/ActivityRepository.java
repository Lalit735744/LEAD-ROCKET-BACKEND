package com.leadrocket.backend.activities.repository;

import com.leadrocket.backend.activities.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity, String> {

	List<Activity> findByLeadId(String leadId);

	List<Activity> findByUserId(String userId);
}
