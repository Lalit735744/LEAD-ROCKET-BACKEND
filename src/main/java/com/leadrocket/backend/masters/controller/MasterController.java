package com.leadrocket.backend.masters.controller;

import com.leadrocket.backend.masters.model.MasterData;
import com.leadrocket.backend.masters.service.MasterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
public class MasterController {

	private final MasterService service;

	public MasterController(MasterService service) {
		this.service = service;
	}

	@GetMapping("/{type}")
	public List<MasterData> getByType(@PathVariable String type) {
		return service.getByType(type);
	}
}
