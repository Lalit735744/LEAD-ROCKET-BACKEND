// BaseEntity
// Shared audit + soft delete fields for Mongo entities

package com.leadrocket.backend.common.model;

import java.time.Instant;

public abstract class BaseEntity {

	// Soft delete flag
	protected boolean deleted = false;

	// Audit fields
	protected String createdBy;
	protected String updatedBy;

	protected Instant createdAt;
	protected Instant updatedAt;

	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }

	public String getCreatedBy() { return createdBy; }
	public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

	public String getUpdatedBy() { return updatedBy; }
	public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

	public Instant getUpdatedAt() { return updatedAt; }
	public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
