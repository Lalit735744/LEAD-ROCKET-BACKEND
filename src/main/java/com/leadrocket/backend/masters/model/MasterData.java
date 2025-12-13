package com.leadrocket.backend.masters.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "masters")
public class MasterData {

	@Id
	private String id;

	private String type;
	private String key;
	private String value;
	private boolean active;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }

	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }

	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
