// Team entity
// Represents a sales or operational team inside a company

package com.leadrocket.backend.teams.model;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Team extends BaseEntity {

	@Id
	private String id;

	// Tenant scope
	private String companyId;

	// Team info
	private String name;

	// Team lead / manager
	private String managerId;

	// Team members (userIds)
	private List<String> memberIds;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getCompanyId() { return companyId; }
	public void setCompanyId(String companyId) { this.companyId = companyId; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getManagerId() { return managerId; }
	public void setManagerId(String managerId) { this.managerId = managerId; }

	public List<String> getMemberIds() { return memberIds; }
	public void setMemberIds(List<String> memberIds) { this.memberIds = memberIds; }
}
