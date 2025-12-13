package com.leadrocket.backend.reports.controller;

import com.leadrocket.backend.reports.dto.ReportResponseDTO;
import com.leadrocket.backend.reports.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

	private final ReportService service;

	public ReportController(ReportService service) {
		this.service = service;
	}

	@GetMapping("/dashboard")
	public ReportResponseDTO dashboard() {
		return service.getDashboardReport();
	}
}
