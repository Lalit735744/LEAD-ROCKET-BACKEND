package com.leadrocket.backend.reports.service;

import com.leadrocket.backend.reports.dto.ReportResponseDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ReportService {

	public ReportResponseDTO getDashboardReport() {
		ReportResponseDTO dto = new ReportResponseDTO();
		dto.setTitle("Dashboard Report");
		dto.setData(new HashMap<>());
		return dto;
	}
}
