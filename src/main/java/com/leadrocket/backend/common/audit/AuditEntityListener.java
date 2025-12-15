package com.leadrocket.backend.common.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.util.Date;

public class AuditEntityListener {

	@PrePersist
	public void beforeCreate(Object obj) {
		if (obj instanceof com.leadrocket.backend.common.model.BaseEntity entity) {
			entity.setCreatedAt(new Date());
			entity.setUpdatedAt(new Date());
			entity.setCreatedBy("SYSTEM"); // later from auth
		}
	}

	@PreUpdate
	public void beforeUpdate(Object obj) {
		if (obj instanceof com.leadrocket.backend.common.model.BaseEntity entity) {
			entity.setUpdatedAt(new Date());
			entity.setUpdatedBy("SYSTEM");
		}
	}
}
