// Tenant-scoped REST APIs for team management

package com.leadrocket.backend.teams.controller;

import com.leadrocket.backend.teams.dto.TeamRequestDTO;
import com.leadrocket.backend.teams.dto.TeamResponseDTO;
import com.leadrocket.backend.teams.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/teams")
public class TenantTeamController {

    private final TeamService service;

    public TenantTeamController(TeamService service) {
        this.service = service;
    }

    @PostMapping
    public TeamResponseDTO create(
            @PathVariable String companyId,
            @RequestBody TeamRequestDTO dto
    ) {
        return service.create(companyId, dto);
    }

    @GetMapping
    public List<TeamResponseDTO> list(@PathVariable String companyId) {
        return service.list(companyId);
    }

    @GetMapping("/{teamId}")
    public TeamResponseDTO get(
            @PathVariable String companyId,
            @PathVariable String teamId
    ) {
        return service.getById(companyId, teamId);
    }
}
