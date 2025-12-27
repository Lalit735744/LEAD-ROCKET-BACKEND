// Activity entity
// Represents a single activity or follow-up done for a lead inside a company

package com.leadrocket.backend.activities.model;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

public class Activity extends BaseEntity {

	@Id
	private String id;

	// Tenant scope
	private String companyId;

	// Business fields
	private String leadId;
	private String userId;
	private String type;      // CALL, VISIT, FOLLOW_UP etc
	private String status;    // PENDING, DONE, CANCELLED
	private String remarks;
	private Date followUpDate;

	// Extra dynamic fields
	private Map<String, Object> metadata;

	// Getters & setters
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getCompanyId() { return companyId; }
	public void setCompanyId(String companyId) { this.companyId = companyId; }

	public String getLeadId() { return leadId; }
	public void setLeadId(String leadId) { this.leadId = leadId; }

	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getRemarks() { return remarks; }
	public void setRemarks(String remarks) { this.remarks = remarks; }

	public Date getFollowUpDate() { return followUpDate; }
	public void setFollowUpDate(Date followUpDate) { this.followUpDate = followUpDate; }

	public Map<String, Object> getMetadata() { return metadata; }
	public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
