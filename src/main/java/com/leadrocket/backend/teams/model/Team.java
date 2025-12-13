package com.leadrocket.backend.teams.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teams")
public class Team {

	@Id
	private String id;

	private String name;
	private String managerId;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getManagerId() { return managerId; }
	public void setManagerId(String managerId) { this.managerId = managerId; }
}
