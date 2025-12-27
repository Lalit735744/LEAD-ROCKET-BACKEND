// Lead entity
// Represents a single lead belonging to a company (tenant scoped)

package com.leadrocket.backend.leads.model;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class Lead extends BaseEntity {

	@Id
	private String id;

	// Tenant scope
	private String companyId;

	// Lead basic info
	private String name;
	private String phone;
	private String email;
	private String source;
	private String status;

	// Assignment
	private String assignedTo;

	// Extra dynamic fields
	private Map<String, Object> metadata;

	// Getters & setters
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getCompanyId() { return companyId; }
	public void setCompanyId(String companyId) { this.companyId = companyId; }

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

	public Map<String, Object> getMetadata() { return metadata; }
	public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
