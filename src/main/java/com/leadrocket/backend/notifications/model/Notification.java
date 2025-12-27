// Notification entity
// Represents an in-app notification for a tenant user

package com.leadrocket.backend.notifications.model;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.annotation.Id;

public class Notification extends BaseEntity {

    @Id
    private String id;

    // Tenant scope
    private String companyId;

    // Sender & receiver
    private String fromUserId;
    private String toUserId;

    // Notification details
    private String type;     // LEAD_ASSIGNED, PAYMENT_DUE etc
    private String title;
    private String message;

    // Read status
    private boolean read = false;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getFromUserId() { return fromUserId; }
    public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }

    public String getToUserId() { return toUserId; }
    public void setToUserId(String toUserId) { this.toUserId = toUserId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
