package com.leadrocket.backend.reports.service;

import java.util.HashMap;

import com.leadrocket.backend.reports.dto.ReportResponseDTO;
import org.springframework.stereotype.Service;
import com.leadrocket.backend.reports.aggregation.LeadReportAggregation;

@Service
public class ReportService {

	private final LeadReportAggregation leadAgg;

	public ReportService(LeadReportAggregation leadAgg) {
		this.leadAgg = leadAgg;
	}

	public Object leadStatusReport() {
		return leadAgg.leadsByStatus();
	}

	public ReportResponseDTO getDashboardReport() {
		ReportResponseDTO dto = new ReportResponseDTO();
		dto.setTitle("Dashboard Report");
		dto.setData(new HashMap<>());
		return dto;
	}
}
