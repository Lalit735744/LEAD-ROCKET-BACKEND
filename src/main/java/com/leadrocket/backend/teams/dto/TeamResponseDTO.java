// DTO returned to frontend for team APIs

package com.leadrocket.backend.teams.dto;

import java.util.List;

public class TeamResponseDTO {

    private String id;
    private String name;
    private String managerId;
    private List<String> memberIds;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public List<String> getMemberIds() { return memberIds; }
    public void setMemberIds(List<String> memberIds) { this.memberIds = memberIds; }
}
