// DTO used to create or update a team

package com.leadrocket.backend.teams.dto;

import java.util.List;

public class TeamRequestDTO {

    private String name;
    private String managerId;
    private List<String> memberIds;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    public List<String> getMemberIds() { return memberIds; }
    public void setMemberIds(List<String> memberIds) { this.memberIds = memberIds; }
}
