package com.leadrocket.backend.activities.dto;

import java.util.Date;
import java.util.Map;

public class ActivityDTO {

	private String id;
	private String leadId;
	private String userId;
	private String activityType;
	private String status;
	private String remarks;
	private Date followUpDate;
	private Date createdAt;
	private Map<String, Object> metadata;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getLeadId() { return leadId; }
	public void setLeadId(String leadId) { this.leadId = leadId; }

	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public String getActivityType() { return activityType; }
	public void setActivityType(String activityType) { this.activityType = activityType; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getRemarks() { return remarks; }
	public void setRemarks(String remarks) { this.remarks = remarks; }

	public Date getFollowUpDate() { return followUpDate; }
	public void setFollowUpDate(Date followUpDate) { this.followUpDate = followUpDate; }

	public Date getCreatedAt() { return createdAt; }
	public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

	public Map<String, Object> getMetadata() { return metadata; }
	public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
