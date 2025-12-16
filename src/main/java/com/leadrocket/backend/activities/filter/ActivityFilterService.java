package com.leadrocket.backend.activities.filter;

import com.leadrocket.backend.activities.model.Activity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.leadrocket.backend.activities.service.ActivityService;
import com.leadrocket.backend.activities.dto.ActivityDTO;

import java.util.Date;
import java.util.List;

@Service
public class ActivityFilterService {

	private final MongoTemplate mongoTemplate;
	private final ActivityService activityService;

	public ActivityFilterService(MongoTemplate mongoTemplate, ActivityService activityService) {
		this.mongoTemplate = mongoTemplate;
		this.activityService = activityService;
	}

	// NOT_DONE activities
	public List<Activity> notDoneActivities(String userId) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("userId").is(userId)
					.and("status").ne("DONE")
		);
		return mongoTemplate.find(query, Activity.class);
	}

	// Overdue followups
	public List<Activity> overdueActivities(String userId) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("userId").is(userId)
					.and("followUpDate").lt(new Date())
					.and("status").ne("DONE")
		);
		return mongoTemplate.find(query, Activity.class);
	}

	// Upcoming followups
	public List<Activity> upcomingActivities(String userId, Date start, Date end) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("userId").is(userId)
					.and("followUpDate").gte(start).lte(end)
		);
		return mongoTemplate.find(query, Activity.class);
	}

	public List<ActivityDTO> getNotDone(String userId) {
		return notDoneActivities(userId)
				.stream()
				.map(activityService::toDTO)
				.toList();
	}

	public List<ActivityDTO> getOverdue(String userId) {
		return overdueActivities(userId)
				.stream()
				.map(activityService::toDTO)
				.toList();
	}

	// Filter by metadata key/value, checks nested metadata.<key>
	public List<Activity> filterByMetadata(String key, Object value) {
		Query query = new Query();
		query.addCriteria(Criteria.where("metadata." + key).is(value));
		return mongoTemplate.find(query, Activity.class);
	}

}
