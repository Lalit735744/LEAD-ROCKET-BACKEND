// MasterData entity
// Represents static configuration data like lead status, sources, activity types

package com.leadrocket.backend.masters.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "masters")
public class MasterData {

	@Id
	private String id;

	// Grouping key (LEAD_STATUS, ACTIVITY_TYPE, SOURCE etc)
	private String type;

	// Machine-readable key
	private String key;

	// Human-readable value
	private String value;

	// Whether this master entry is active
	private boolean active = true;

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
