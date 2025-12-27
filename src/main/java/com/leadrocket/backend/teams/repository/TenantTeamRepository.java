// Tenant-aware repository for teams

package com.leadrocket.backend.teams.repository;

import com.leadrocket.backend.teams.model.Team;

import java.util.List;
import java.util.Optional;

public interface TenantTeamRepository {

    Team save(String companyId, Team team);

    Optional<Team> findById(String companyId, String teamId);

    List<Team> findAll(String companyId);
}
