// DTO used when creating or updating an activity

package com.leadrocket.backend.activities.dto;

import java.util.Date;
import java.util.Map;

public class ActivityRequestDTO {

    private String leadId;
    private String userId;
    private String type;
    private String status;
    private String remarks;
    private Date followUpDate;
    private Map<String, Object> metadata;

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
