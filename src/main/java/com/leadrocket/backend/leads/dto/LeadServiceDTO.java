package com.leadrocket.backend.leads.dto;

import java.util.Date;
import java.util.Map;

public class LeadServiceDTO {

	private String id;
	private String name;
	private String phone;
	private String email;
	private String source;
	private String status;
	private String assignedTo;
	private Date createdAt;
	private Date updatedAt;
	private Map<String, Object> metadata;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getSource() { return source; }
	public void setSource(String source) { this.source = source; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

	public String getAssignedTo() { return assignedTo; }
	public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

	public Date getCreatedAt() { return createdAt; }
	public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

	public Date getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

	public Map<String, Object> getMetadata() { return metadata; }
	public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
